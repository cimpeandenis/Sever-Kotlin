from django.forms import model_to_dict
from django.views.decorators.csrf import csrf_exempt
from rest_framework.decorators import api_view
from rest_framework.response import Response

from computers.models import Accessory


@api_view(['GET'])
def get_all(request):
    computers_qs = Accessory.objects.all()
    result = list()
    for item in computers_qs:
        dict_model = model_to_dict(item)
        result.append(dict_model)
    return Response(result)


@api_view(['POST'])
@csrf_exempt
def add_computer(request):
    data = request.data
    data.pop('id', None)
    computer = Accessory(**data)
    computer.save()
    return Response(model_to_dict(computer))


@api_view(['DELETE'])
@csrf_exempt
def remove_computer(request, id):
    Accessory.objects.get(pk=id).delete()
    return Response(id)


@api_view(['PUT'])
@csrf_exempt
def update_computer(request, id):
    c = Accessory.objects.get(pk=id)
    c.phone = request.data['phone']
    c.accessory = request.data['accessory']
    c.save()
    return Response(id)
