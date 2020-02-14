"""
     서버 API 요청 소스코드(테스트)
     간단한 회귀 모델로 작성함, 요청 주소로는 predict 또는 hi

     자신의 테스트 환경에 맞춰서 IP와 PORT 수정 필요
"""
from sshtunnel import SSHTunnelForwarder
import requests

SERVER_IP = ''
SERVER_PORT = '5000'
SERVER_USER = ''
SERVER_PWD = ''

server = SSHTunnelForwarder(ssh_address_or_host = SERVER_IP, 
                            ssh_username = SERVER_USER,
                            ssh_password = SERVER_PWD,
                            remote_bind_address = ('127.0.0.1', SERVER_PORT))

server.start()

url_pred = f'http://127.0.0.1:{server.local_bind_port}/predict'
url_hi = f'http://127.0.0.1:{server.local_bind_port}/hi'

X = {'ntceInsttNm' : 1, 
     'dminsttNm' : 36, 
     'year' : 2012, 
     'month' : 1, 
     'day' : 20, 
     'hour' : 10, 
     'minute' : 30}

try:
    print('---------------------------')
    r = requests.get(url_hi)
    print(r.text)
    print('---------------------------')
    r = requests.get(url_pred, json = X)
    print(r.json()[0])
    print('---------------------------')
except Exception as e:
    print(e)
finally:
    server.stop()
