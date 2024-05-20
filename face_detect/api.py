from typing import Union
from fastapi import FastAPI, UploadFile
from fastapi.middleware.cors import CORSMiddleware
from contextlib import asynccontextmanager
from pydantic import BaseModel
import os
from dotenv import load_dotenv
import boto3
import uvicorn
from datetime import datetime, timedelta
from face_detect import ImageBlur
import cv2
from fastapi.responses import JSONResponse, FileResponse
import numpy as np
import uuid
import urllib

class Image(BaseModel):
    query: str
    target_lang: str

@asynccontextmanager
async def lifespan(app:FastAPI):
    global llm
    
    yield

app = FastAPI(lifespan=lifespan)

filter = ImageBlur()

load_dotenv()

s3 = boto3.client("s3", aws_access_key_id=os.getenv('S3_ACCESS_KEY'), aws_secret_access_key=os.getenv('S3_SECRET_KEY'))

@app.get("/")
async def initiate():
    return

@app.post("/filter")
async def filter_image(file: UploadFile):

    content = await file.read()

    nparr = np.frombuffer(content, np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    filename = f'{uuid.uuid4()}.jpg'
    filtered_img = filter.blur(img)
    cv2.imwrite(filename, filtered_img)

    try:
        s3.upload_file(f'{filename}', os.getenv('S3_BUCKET_NAME'), filename, ExtraArgs={'ContentType': 'image/jpeg'})
    except:
        os.remove(f'{filename}')
        return JSONResponse(status_code=500, content={'msg': 'failed to upload image'})
    
    url = "https://s3-ap-northeast-2.amazonaws.com/%s/%s" % (
        os.getenv('S3_BUCKET_NAME'),
        urllib.parse.quote(filename, safe="~()*!.'")
    )


    os.remove(f'{filename}')
    return JSONResponse(status_code=200, content={'msg': 'success', 'url': url})



if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)