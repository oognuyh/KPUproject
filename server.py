from gensim.models.doc2vec import Doc2Vec
from flask import Flask
from flask_restful import Resource, Api
from flask_restful import reqparse

"""
    FILE LOCATION
"""
# pickle로 수정할 것
DOC2VEC_MODEL_LOCATION = 'doc2vec.model'

app = Flask(__name__)
api = Api(app)
model = Doc2Vec.load(DOC2VEC_MODEL_LOCATION)

class Recommender(Resource):
    def post(self):
        parser = reqparse.RequestParser()
        parser.add_argument('contentId', type = str)

        args = parser.parse_args()
        content_id = int(args['contentId'])
        recommender = model.docvecs.most_similar(content_id, topn = 10)
        recommended = []
        for content in recommender:
            recommended.append(str(content[0]))

        return {'recommended' : recommended}
 
api.add_resource(Recommender, '/recommend')

if __name__ == '__main__':
    app.run(host = '0.0.0.0', debug = True)
    