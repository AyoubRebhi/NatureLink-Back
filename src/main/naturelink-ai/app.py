# app.py
from flask import Flask, request, jsonify
from recommender import ActivityRecommender

import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity


app = Flask(__name__)
recommender = ActivityRecommender("activities.csv")

@app.route("/recommend", methods=["POST"])
def recommend():
    data = request.get_json()
    mood_input = data.get("mood_input", "")
    activity_data = data.get("activities", [])

    df = pd.DataFrame(activity_data)
    df.fillna('', inplace=True)

    def weighted_combine(row):
        mood = ' '.join([f"{m} " * 3 for m in row['mood'].split(',')]) if isinstance(row['mood'], str) else ''
        tags = ' '.join([f"{t} " * 2 for t in row['tags'].split(',')]) if isinstance(row['tags'], str) else ''
        return f"{row['type']} {mood} {tags} {row['description']}"

    df['combined'] = df.apply(weighted_combine, axis=1)
    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform(df['combined'])
    input_vec = vectorizer.transform([mood_input])
    similarities = cosine_similarity(input_vec, vectors).flatten()

    df['similarity'] = similarities
    top_df = df.sort_values(by='similarity', ascending=False).head(5)
    return jsonify(top_df.to_dict(orient="records"))


if __name__ == '__main__':
    app.run(port=5005)
