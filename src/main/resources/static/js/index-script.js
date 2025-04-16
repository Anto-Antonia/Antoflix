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

// script for interactive movie cards
const movieModal = document.getElementById('movie-modal');
const closeMovieModal = document.getElementById('close-movie-modal');
const movieTitle = document.getElementById('movie-title');
const movieDescription = document.getElementById('movie-description');
const movieGenre = document.getElementById('movie-genre');
const movieTime = document.getElementById('movie-time');
const modalContent = movieModal.querySelector('.modal-content'); // Define modalContent

// Close modal when clicking outside modal content
movieModal.addEventListener('click', (e) => {
  if (!modalContent.contains(e.target)) { // Check if the click is outside modal content
    movieModal.classList.add('hidden');
  }
});

document.querySelectorAll('.movie-card').forEach(card => {
  card.addEventListener('click', () => {
    const title = card.getAttribute('data-title') || "Unknown Movie";
    const desc = card.getAttribute('data-description') || "No description.";
    const genre = card.getAttribute('data-genre') || "N/A";
    const time = card.getAttribute('data-time') || "No date time release.";

    movieTitle.textContent = title;
    movieDescription.textContent = desc;
    movieGenre.textContent = genre;
    movieTime.textContent = time;

    const genreContainer = document.getElementById('movie-genre');
    genreContainer.innerHTML = ''; // clear old genre(s)

    genre.split(',').forEach(genres => {
      const tag = document.createElement('span');
      tag.classList.add('genre-tag');
      tag.textContent = genres.trim();
      genreContainer.appendChild(tag);
    });

    movieModal.classList.remove('hidden');
  });
});

// Close the modal when the close button (X) is clicked
closeMovieModal.addEventListener('click', () => {
  movieModal.classList.add('hidden');
});