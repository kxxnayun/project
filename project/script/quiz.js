function setupQuiz(formId, correctValue, resultBoxId) {
    const quizForm = document.getElementById(formId);

    quizForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const selectedOption = quizForm.querySelector('input[type="radio"]:checked');
        const resultBox = document.getElementById(resultBoxId);

        if (selectedOption.value === correctValue) {
            resultBox.innerText = "🎉 정답입니다!";
            resultBox.className = "result correct";
        } else {
            resultBox.innerText = "❌ 오답이에요!";
            resultBox.className = "result wrong";
        }
    });
}

setupQuiz("quiz1", "0909", "result1");
setupQuiz("quiz2", "baseball", "result2");
setupQuiz("quiz3", "tokyo", "result3");
