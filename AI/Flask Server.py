from flask import Flask, request, jsonify
import random
import pandas as pd
import numpy as np
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import json
from flask import jsonify

app = Flask(__name__)

tokenizer = Tokenizer(num_words=30000)

result_emotion = {0: "공포", 1: "분노", 2: "슬픔", 3: "중립", 4: "행복"}

model = load_model("C:/Users/gisu/Desktop/analysis_model.h5")

# 감정 카테고리에 따른 오늘의 한마디
emotion_comments = {
    "행복": ["오늘도 행복한 일들이 가득하길 바라요.", "긍정적인 에너지를 유지하세요.", "웃음이 계속되길 기대해봐요."],
    "분노": ["마음을 정리하고 힘내세요.", "분노를 이해합니다. 누군가에게 속 시원하게 털어놓아보세요.", "화를 내다보면 가끔 해소되기도 합니다."],
    "슬픔": ["슬픔이 가시길 바라요. 언제든지 이야기해도 좋아요.", "마음이 힘들 때는 편안한 곳에서 휴식을 취하세요.", "슬픔은 지나가기 마련이에요."],
    "중립": ["오늘은 중립적인 기분인가봐요. 편안한 하루 보내세요.", "평범한 하루가 가장 특별한 순간일지도 몰라요.", "중립적인 감정도 소중해요."],
    "공포": ["무서운 감정이군요. 안전한 환경에서 편안함을 찾아보세요.", "무서움을 이해해요. 함께 이야기해보는 것도 도움이 될 수 있어요.", "우리 함께 무서움을 이겨봐요."],
}

# 음식 카테고리에 따른 음식 목록
food_categories = {
    "한식": ["비빔밥", "불고기", "김치찌개", "떡볶이", "삼겹살", "전"],
    "중식": ["짜장면", "탕수육", "양장피", "깐풍기", "마파두부", "볶음밥"],
    "일식": ["초밥", "라멘", "돈까스", "우동", "텐동"],
    "양식": ["스테이크", "파스타", "피자", "카레"],
    "전체": ["비빔밥", "불고기", "김치찌개", "떡볶이", "삼겹살", "전", "짜장면", "탕수육", "양장피", "깐풍기", "마파두부", "볶음밥", "초밥", "라멘", "돈까스", "스테이크", "파스타", "피자"]
}

emotion_categories = {
    "행복": ["아이스크림", "초콜릿", "파스타", "피자"],
    "슬픔": ["토마토 수프", "죽", "따뜻한 차"],
    "공포": ["그릴치즈샌드위치", "허브차", "치킨 스프"],
    "중립": ["샐러드", "닭가슴살"],
    "분노": ["라면", "불닭볶음면", "떡볶이", "마라탕"]
}

emotion_food_comments = {
    "아이스크림": ["상쾌한 맛으로 기분을 더 환상적으로 만들어줄 수 있어요."],
    "초콜릿": ["달콤한 초콜릿은 행복한 기분을 더해줄 수 있습니다."],
    "파스타": ["특히 크림 파스타나 토마토 소스 파스타는 여운이 남아 기분 좋게 만들어줄 수 있어요."],
    "피자": ["다양한 토핑의 조합으로 특별한 날을 기념하는 데 좋아요."],

    "토마토 수프": ["따뜻한 수프는 마음을 안정시켜줄 수 있어요."],
    "죽": ["부드럽고 가벼운 음식으로 위로가 되는 효과가 있습니다."],
    "따뜻한 차": ["칼로리 없는 차는 마음을 가라앉히는 데 도움이 됩니다."],

    "그릴치즈샌드위치": ["치즈의 풍미와 따뜻한 빵이 안심감을 주는 음식 중 하나입니다."],
    "허브차": ["차 한 잔은 여유를 가져다줄 수 있습니다."],
    "치킨 수프": ["온기 있고 고소한 향이 나는 스프는 포근한 느낌을 줄 수 있습니다."],

    "샐러드": ["과도하지 않은 식사로 가벼움을 유지할 수 있어요."],
    "닭가슴살": ["과도하지 않은 식사로 가벼움을 유지할 수 있어요."],

    "라면": ["강렬한 맛이 감정을 다루는 데 도움을 줄 수 있습니다."],
    "불닭볶음면": ["매운 음식은 분노를 터뜨리는 데 도움이 될 수 있어요."],
    "떡볶이": ["강렬한 맛이 감정을 다루는 데 도움을 줄 수 있습니다."],
    "마라탕": ["매운 음식은 분노를 터뜨리는 데 도움이 될 수 있어요."],
}

def recommend_food(emotion, cuisine):
    food_list = food_categories[cuisine]
    return random.choice(food_list)

@app.route("/test2")
def test():
    sentence = request.args.get('sentence')
    foodtype = request.args.get('foodtype')

    # 텍스트가 없는 경우
    if not sentence:
        return "No sentence parameter"

    if not foodtype:
        return "No foodtype parameter"

    test_sentence = sentence.split(' ')
    test_sentences = []
    now_sentence = []
    for word in test_sentence:
        now_sentence.append(word)
        test_sentences.append(now_sentence[:])

    test_X_1 = tokenizer.texts_to_sequences(test_sentences)
    test_X_1 = pad_sequences(test_X_1, padding='post', maxlen=8)
    prediction = model.predict(test_X_1)

    for idx, sentence in enumerate(test_sentences):
        result = prediction[idx]
        res_ind = np.argmax(result)

    cuisine_input = foodtype
    cuisine = cuisine_input if cuisine_input in food_categories else "전체"

    def recommend_food(emotion, cuisine):
        food_list = food_categories[cuisine]
        return random.choice(food_list)

    emotion_category = result_emotion[res_ind]
    probability = prediction[0][res_ind] * 100
    print(f"당신의 감정이 {emotion_category}일 확률은 {probability:.2f}%입니다.")

    print("\n")

    # 랜덤으로 음식 하나 추천
    recommended_food = recommend_food(emotion_category, cuisine)
    print("당신의 감정에 어울리는",cuisine,"음식 추천:",recommended_food)


    emotion_food_list = emotion_categories.get(emotion_category, [])
    selected_food = random.choice(emotion_food_list)
    # 랜덤으로 음식과 관련된 한마디 출력
    food_comments = emotion_food_comments.get(selected_food, ["음식에 관련된 한마디를 찾을 수 없습니다."])
    random_food_comment = random.choice(food_comments)
    # 감정 카테고리에 따라 오늘의 한마디 출력
    random_comment = random.choice(emotion_comments.get(emotion_category, ["해당하는 감정 카테고리의 한마디를 찾을 수 없습니다."]))

    data = '{} {} {} {} {}'.format(emotion_category, probability, recommended_food, food_comments, selected_food)

    result = {"data":data}
    return jsonify(result)

    
if __name__ == '__main__':
    app.run(debug=True)

