# ---------------------------------------------------------- #
#             Doc2vec을 이용한 추천 시스템 구현                #
# ---------------------------------------------------------- #
from gensim.models.doc2vec import Doc2Vec, TaggedDocument, TaggedLineDocument
from konlpy.tag import Okt
from math import ceil
from sys import exit
import pandas as pd
import pickle

"""
    FILE LOCATION
"""
TOKENIZED_DETAIL_COMMON = 'tokenized_detail_common.csv'
DETAIL_COMMON = 'detail_common.csv'
DOC2VEC_MODEL = 'doc2vec.pkl'

class RecommenderWithDo2vec():
    def __init__(self):
        self.model = None 
        self.data = None 

    def tokenize_data(self):
        # 토큰화해서 'tknzd_overview' 열에 추가 후 토큰화된 데이터 $TOKENIZED_DETAIL_COMMON으로 저장
        okt = Okt()

        self.data['tknzd_overview'] = self.data['overview'].apply(lambda x: okt.nouns(x))
        self.data.to_csv(TOKENIZED_DETAIL_COMMON, index = False)

    def load_tokenized_data(self):
        # 토큰화된 데이터 파일 존재 여부 확인
        try:            
            self.data = pd.read_csv(TOKENIZED_DETAIL_COMMON)
        except FileNotFoundError:    
            try:
                self.data = pd.read_csv(DETAIL_COMMON)        
            except FileNotFoundError:
                print(f"ERR - Please check the path of {DETAIL_COMMON}")
                exit(0)

            self.tokenize_data()

    def train_model(self, tagged_document, epochs = 20):
        self.model.train(tagged_document, epochs = epochs, total_examples = self.model.corpus_count)

    def save_model(self):
        with open(DOC2VEC_MODEL, "wb") as f:
            pickle.dump(self.model, f,pickle.HIGHEST_PROTOCOL)

    def build_model(self, window = 6, vector_size = 200, alpha = 0.025, min_alpha = 0.025,
                    min_count = 5, dm = 1, seed = 1004):

        self.load_tokenized_data()

        self.model = Doc2Vec(window = window, vector_size = vector_size, alpha = alpha, 
                             min_alpha = min_alpha, min_count = min_count, dm = dm, seed = seed)
        
        tagged_document = [TaggedDocument(words = overview, tags = [content_id]) for content_id, overview in self.data[['contentid', 'tknzd_overview']].values.tolist()]
        self.model.build_vocab(tagged_document)

        self.train_model(tagged_document)

        self.save_model()

    def recommend(self, title, topn = 10):
        content_id = self.data.loc[self.data["title"] == title, "contentid"]
        recommender = self.model.docvecs.most_similar(content_id, topn = topn)

        print('+-----------------------------------+')
        print(f'            {title}          ')
        print('+-----------------------------------+')
        for recommended in recommender:
            recommended_title = self.data.loc[self.data["contentid"] == recommended[0], "title"]
            similar = ceil(recommended[1] * 100)
            print(f'{recommended_title} - {similar} %')
        print('+-----------------------------------+')

if __name__ == "__main__":
    recommender = RecommenderWithDo2vec()
    recommender.build_model()
    recommender.recommend("남수원컨트리클럽")