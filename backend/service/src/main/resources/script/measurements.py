import time
import requests


class Measurement:
    def __init__(self):
        self.attributes = {
            'waterLvl': 30,
            'waterTemp': 5,
            'waterSpeed': 5,
            'windSpeed': 5
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
    increasing_w_lvl = True
    w_lvl_cnt = 0
    increment = 10

    while True:
        send_measurement_to_spring_boot(measurement)

        for attribute in measurement.attributes:
            value = measurement.attributes[attribute]
            if attribute == 'waterLvl':
                if value > 80:
                    value = 30
                    w_lvl_cnt += 1
                elif value < 30:
                    value = 80
                    w_lvl_cnt += 1

                value += increment if increasing_w_lvl else -increment
                if w_lvl_cnt == 3:
                    increasing_w_lvl = not increasing_w_lvl
                    w_lvl_cnt = 0
            else:
                value += increment if increasing else -increment

                if value > 100:
                    value = 100
                    increasing = False
                elif value < 0:
                    value = 0
                    increasing = True

            measurement.attributes[attribute] = value

        time.sleep(2)


generate_measurements()
