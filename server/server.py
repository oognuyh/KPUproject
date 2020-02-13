"""
    ML API TEST CODE(SERVER)
    
    02-02-2020
"""
from flask import Flask, request, jsonify
import numpy as np 
import pandas as pd 
import pickle

app = Flask(__name__)
model = pickle.load(open('./model/model.pkl', 'rb'))

@app.route('/predict')
def predict():
    data = request.get_json(force = True)
    X = pd.DataFrame(data, index = [0])
    pred = model.predict(X)
    output = pred
    return f"{output}"

@app.route('/hi')
def test():
    return 'hi'

if __name__ == '__main__':
    app.run(host = '0.0.0.0')