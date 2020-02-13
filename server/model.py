"""
    ML API TEST CODE(MODEL)
    
    02-02-2020
"""
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import LabelEncoder
import pandas as pd 
import numpy as np 
import pickle, os

data = pd.read_csv('~/api_test/data/result2.csv')
data = data.astype({'bssamt' : np.int64,
                    'bsisPlnprc' : np.int64,
                    'plnprc' : np.int64,
                    'totRsrvtnPrceNum' : np.int32,
                    'compnoRsrvtnPrceSno' : np.int32})
data['rlOpengDt'] = pd.to_datetime(data['rlOpengDt'])

"""
    공고번호-차수를 기준으로 데이터를 줄인다.
    rate = plnprc / bssamt (소수점 5 자리에서 올림)
"""
reduce_data = data.drop_duplicates(['bidNtceNo', 'bidNtceOrd'], keep = 'first').copy()
reduce_data['rate'] = reduce_data['plnprc'] / reduce_data['bssamt']
reduce_data['rate'] = (np.ceil(reduce_data['rate'] * 1000000) / 10000)

reduce_data['year'] = reduce_data['rlOpengDt'].dt.year
reduce_data['month'] = reduce_data['rlOpengDt'].dt.month
reduce_data['day'] = reduce_data['rlOpengDt'].dt.day
reduce_data['hour'] = reduce_data['rlOpengDt'].dt.hour
reduce_data['minute'] = reduce_data['rlOpengDt'].dt.minute

X = reduce_data[['ntceInsttNm', 'dminsttNm', 'year', 'month', 'day', 'hour', 'minute']].copy()
Y = reduce_data['rate'].copy()

X = X.reset_index()
Y = Y.reset_index()

del X['index'], Y['index']

le_ntceInsttNm = LabelEncoder()
X['ntceInsttNm'] = le_ntceInsttNm.fit_transform(X['ntceInsttNm'])

le_dminsttNm = LabelEncoder()
X['dminsttNm'] = le_dminsttNm.fit_transform(X['dminsttNm'])

X[['ntceInsttNm', 'dminsttNm']] = X[['ntceInsttNm', 'dminsttNm']].astype('category')

X_train, X_test, y_train, y_test = train_test_split(X, Y, test_size = 0.3, random_state = 1004)

model = LinearRegression()
model.fit(X_train, y_train)

y_pred = model.predict(X_test)

# joblib.dump(model, '~/api_test/model/model.pkl')
# pickle.dump(model, open('~/api_test/model/model.pkl', 'wb'))

with open('./model/model.pkl', "wb") as f:
    pickle.dump(model, f, pickle.HIGHEST_PROTOCOL)