## 소개
### 해양 생물 보호 목적으로 ,플라스틱 사용을 줄이기 위한 환경 실천 계획을 세울 수 있는 앱입니다.
### 혼자서 실천이 어려운 다수를 고려해 친구, 가족 등 좋아하는 사람들과 함께 진행하도록 설계했습니다.
### 제로웨이스트 샵, 기업의 환경 이벤트 등 쉽고 재미있게 실천할 수 있는 환경 활동에 대한 정보를 제공하여 놀이 문화로 일상 속에서 자주 실천하도록 하는 것이 목표입니다.

## 개발 툴
### Android Studio(Kotlin), Firebase

## How to run
### 0️⃣ git clone https://github.com/UnderTheSea-E/UnderTheSea_AOS/ using git bash.
### 1️⃣ Open in android studio.
### 2️⃣ Download key files in shared google drive folderr, UnderTheSea(.gitignore files) - Google Solution Challenge 2023. (We wrote google drive link in answer to the question 'Share your GitHub Project Link' in Google Developer Student Clubs Solution Challenge 2023 - Submission Form)
### 3️⃣ Put 'google-services.json' file in app/.
### 4️⃣ Put 'local.properties' file in in project level folder(UnderTheSea_AOS/).
### 5️⃣ Put 'kakaoApi.xml' file in app/src/main/res/values.
### 6️⃣ If you want to connect to our gcp server, open app/src/main/java/com/example/underthesea_aos/retrofit/RetrofitBuilder.kt and change baseUrl as http://34.22.70.141:3000/. If you try to connect to your local server, change baseUrl as http://10.0.2.2:port/ and run.
