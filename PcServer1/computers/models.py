from django.db import models


# Create your models here.
class Accessory(models.Model):
    # model = models.CharField(max_length=200)
    # brand = models.CharField(max_length=200)
    # is_functional = models.BooleanField()
    # created_at = models.CharField(max_length=200)
    phone = models.CharField(max_length=200)
    accessory = models.CharField(max_length=200)
