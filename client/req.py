"""
     서버 API 요청 소스코드(테스트)
     간단한 회귀 모델로 작성함, 요청 주소로는 predict 또는 hi

     자신의 테스트 환경에 맞춰서 IP와 PORT 수정 필요
"""
import requests

SERVER_IP = '192.168.1.51'
SERVER_PORT = '5000'

url = f'http://{SERVER_IP}:{SERVER_PORT}/predict'

X = {'ntceInsttNm' : 1, 
     'dminsttNm' : 36, 
     'year' : 2012, 
     'month' : 1, 
     'day' : 20, 
     'hour' : 10, 
     'minute' : 30}

Y = requests.get(url, json = X)

print(Y.json())
