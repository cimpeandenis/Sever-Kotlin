# Generated by Django 3.0 on 2019-12-08 15:18

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('computers', '0001_initial'),
    ]

    operations = [
        migrations.RenameField(
            model_name='computer',
            old_name='isFunctional',
            new_name='is_functional',
        ),
    ]
