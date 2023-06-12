import time
import requests

class Measurement:
    def __init__(self):
        self.attributes = {
            'waterLvl': 0,
            'waterTemp': 0,
            'waterSpeed': 0,
            'windSpeed': 0,
            'waterFlow': 0,
            'pressure': 0
        }

def send_measurement_to_spring_boot(measurement):
    url = 'http://localhost:8080/api/measurements'
    payload = measurement.attributes
    headers = {'Content-Type': 'application/json'}
    response = requests.post(url, json=payload, headers=headers)
    if response.status_code == 200:
        print('Measurement sent successfully')
    else:
        print('Failed to send measurement')

def generate_measurements():
    measurement = Measurement()
    increasing = True
    increment = 5

    while True:
        send_measurement_to_spring_boot(measurement)

        for attribute in measurement.attributes:
            value = measurement.attributes[attribute]
            value += increment if increasing else -increment

            if value > 100:
                value = 100
                increasing = False
            elif value < 0:
                value = 0
                increasing = True

            measurement.attributes[attribute] = value

        time.sleep(5)

generate_measurements()
