
import face_recognition
import cv2
from PIL import Image, ImageDraw
import numpy as np

class ImageBlur():
    def __init__(self) -> None:
        pass

    def blur(self, image):
        face_locations = face_recognition.face_locations(image, number_of_times_to_upsample=0, model="cnn") 
        for face_location in face_locations:
            top, right, bottom, left = face_location 
            face_image = image[top:bottom, left:right]
            face_image = cv2.blur(face_image, (50, 50))
            image[top:bottom, left:right] = face_image

        return image