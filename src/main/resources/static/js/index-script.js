const arrowLeft = document.querySelector('.arrow-left');
const arrowRight = document.querySelector('.arrow-right');
const movieCards = document.querySelector('.movie-cards');

// Width of one movie card + margin
const cardWidth = 200 + 15; // Adjust based on your CSS
const visibleCards = 5; // Number of visible cards
const maxIndex = movieCards.children.length - visibleCards; // Max index based on number of visible cards

let currentIndex = 0;

// Set the width of the movie-cards container to fit only visibleCards
movieCards.style.width = `${cardWidth * movieCards.children.length}px`;

// Handle left arrow click
arrowLeft.addEventListener('click', () => {
    if (currentIndex > 0) {
        currentIndex--;
        movieCards.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
    }
});

// Handle right arrow click
arrowRight.addEventListener('click', () => {
    if (currentIndex < maxIndex) {
        currentIndex++;
        movieCards.style.transform = `translateX(-${currentIndex * cardWidth}px)`;
    }
});