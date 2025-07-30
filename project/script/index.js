function showPage(pageName) {
    switch (pageName) {
        case 'profile':
            window.location.href = '../html/profile.html';
            break;
        case 'travel':
            window.location.href = '../html/travel.html';
            break;
        case 'likes':
            window.location.href = '../html/likes.html';
            break;
        default:
            break;
    }
}