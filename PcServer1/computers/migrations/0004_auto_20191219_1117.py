# Generated by Django 2.1 on 2019-12-19 09:17

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('computers', '0003_computer_created_at'),
    ]

    operations = [
        migrations.CreateModel(
            name='Accessory',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('phone', models.CharField(max_length=200)),
                ('accessory', models.CharField(max_length=200)),
            ],
        ),
        migrations.DeleteModel(
            name='Computer',
        ),
    ]
