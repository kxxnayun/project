const imageList = document.querySelectorAll('.slide img');
const slideWrapper = document.querySelector('.slides');
const textBox = document.getElementById('slideText');
let currentSlide = 0;

function showSlide() {
    slideWrapper.style.transform = 'translateX(-' + currentSlide * 100 + '%)';
}

document.getElementById('preButton').addEventListener('click', function () {
    currentSlide = currentSlide - 1;
    if (currentSlide < 0) {
        currentSlide = imageList.length - 1;
    }
    showSlide();
});

document.getElementById('nextButton').addEventListener('click', function () {
    currentSlide = currentSlide + 1;
    if (currentSlide >= imageList.length) {
        currentSlide = 0;
    }

    showSlide();
});