const travels = [
    {
        title: "일본 오사카",
        region: "japan",
        year: "2023",
        images: ["../media/japan1.jpg", "../media/japan2.jpg", "../media/japan3.jpg", "../media/japan4.jpg", "../media/japan5.jpg", "../media/japan6.jpg", "../media/japan7.jpg", "../media/japan8.jpg", "../media/japan9.jpg", "../media/japan10.jpg", "../media/japan11.jpg"],
        thumbnail: "../media/japan5.jpg"
    },
    {
        title: "전라남도 전주",
        region: "korea",
        year: "2024",
        images: ["../media/jeonju1.jpg", "../media/jeonju2.jpg", "../media/jeonju3.jpg", "../media/jeonju4.jpg", "../media/jeonju5.jpg", "../media/jeonju7.jpg", "../media/jeonju8.jpg"],
        thumbnail: "../media/jeonju4.jpg"
    },
    {
        title: "홍콩",
        region: "hongkong",
        year: "2024",
        images: ["../media/hk1.jpg", "../media/hk3.jpg", "../media/hk4.jpg", "../media/hk5.jpg", "../media/hk6.jpg", "../media/hk7.jpg", "../media/hk8.jpg", "../media/hk10.jpg", "../media/hk11.jpg"],
        thumbnail: "../media/hk7.jpg"
    },
    {
        title: "대전광역시",
        region: "korea",
        year: "2025",
        images: ["../media/dj1.jpg", "../media/dj3.jpg", "../media/dj4.jpg", "../media/dj5.jpg", "../media/dj6.jpg", "../media/dj7.jpg", "../media/dj8.jpg", "../media/dj9.jpg", "../media/dj10.jpg"],
        thumbnail: "../media/dj1.jpg"
    },
    {
        title: "광주광역시",
        region: "korea",
        year: "2025",
        images: ["../media/gj2.jpg", "../media/gj1.jpg", "../media/gj3.jpg", "../media/gj4.jpg", "../media/gj5.jpg", "../media/gj6.jpg", "../media/gj7.jpg", "../media/gj8.jpg"],
        thumbnail: "../media/gj2.jpg"
    }
];

function showTravels() {
    let list = document.getElementById("travelList");
    list.innerHTML = "";

    let region = document.getElementById("regionFilter").value;
    let year = document.getElementById("yearFilter").value;

    let filtered = travels.filter(function(item) {
        let okRegion = (region == "all" || item.region == region);
        let okYear = (year == "all" || item.year == year);
        return okRegion && okYear;
    });

    filtered.forEach(function(item) {
        let card = document.createElement("div");
        card.className = "travelCard";
        
        card.innerHTML = '<img src="' + item.thumbnail + '"><h3>' + item.title + '</h3><button onclick="changeLike(this)">🤍 좋아요</button>';

        card.querySelector("img").addEventListener("click", function() {
            openImage(item.images, 0);
        });

        list.appendChild(card); 
    });
}

function changeLike(btn) {
    if (btn.textContent.indexOf("❤️") != -1) { 
        btn.textContent = "🤍 좋아요";
    } else {
        btn.textContent = "❤️ 좋아요";
    }
}

function closeModal() {
    let modal = document.getElementById("modal");
    modal.classList.add("hidden");
}

document.getElementById("regionFilter").addEventListener("change", showTravels);
document.getElementById("yearFilter").addEventListener("change", showTravels);

window.onload = showTravels;

let imagesForModal = [];
let currentIndex = 0;

function openImage(images, index) {
    imagesForModal = images;
    currentIndex = index;
    document.getElementById("modalImage").src = imagesForModal[currentIndex];
    document.getElementById("modal").classList.remove("hidden");
}

document.getElementById("preButton").addEventListener("click", function() {
    if (imagesForModal.length == 0) 
        return;

    currentIndex--;

    if (currentIndex < 0) 
        currentIndex = imagesForModal.length - 1;

    document.getElementById("modalImage").src = imagesForModal[currentIndex];
});

document.getElementById("nextButton").addEventListener("click", function() {
    if (imagesForModal.length == 0) 
        return;

    currentIndex++;

    if (currentIndex >= imagesForModal.length) 
        currentIndex = 0;

    document.getElementById("modalImage").src = imagesForModal[currentIndex];
});
