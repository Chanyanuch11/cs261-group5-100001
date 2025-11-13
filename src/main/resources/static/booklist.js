document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("bookList");

  fetch('http://localhost:8080/api/books')
    .then(res => res.json())
    .then(books => {
      container.innerHTML = ""; // ล้าง container ก่อน

      books.forEach(book => {
        const card = document.createElement("div");
        card.className = "book-card";

        card.innerHTML = `
          <img src="${book.coverUrl}" alt="${book.title}" width="120" height="160">
          <div class="info">
            <div class="title">${book.title}</div>
            <div class="author">${book.author}</div>
            <div class="price">฿${book.price}</div>
          </div>
          <div class="stock-book">
            <i class="bi bi-dash-circle-fill"></i>
            <div class="quantity">0</div>
            <i class="bi bi-plus-circle-fill"></i>
          </div>
        `;

        container.appendChild(card);
      });
    })
    .catch(err => console.error("Error fetching books:", err));
});

