from django.urls import path

from . import views

urlpatterns = [
    path('', views.get_all),
    path('new/', views.add_computer),
    path('delete/<int:id>', views.remove_computer),
    path('update/<int:id>', views.update_computer)
]
