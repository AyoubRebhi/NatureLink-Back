# recommender.py
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

class ActivityRecommender:
    def __init__(self, csv_path):
        self.df = pd.read_csv(csv_path)
        self.df.fillna('', inplace=True)
        self.vectorizer = TfidfVectorizer(stop_words='english')
        self.df['combined'] = (
                self.df['type'] + ' ' + self.df['mood'] + ' ' + self.df['tags'] + ' ' + self.df['description']
        ).str.lower()
        self.activity_vectors = self.vectorizer.fit_transform(self.df['combined'])

    def recommend(self, user_input, top_n=5):
        input_vector = self.vectorizer.transform([user_input.lower()])
        similarities = cosine_similarity(input_vector, self.activity_vectors).flatten()
        self.df['similarity'] = similarities
        recommendations = self.df.sort_values(by='similarity', ascending=False).head(top_n)
        return recommendations.to_dict(orient='records')
