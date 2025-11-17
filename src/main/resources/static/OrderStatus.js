const API_BASE_URL = 'http://localhost:8080/api';


function getUserId() {
  const id = Number(localStorage.getItem('userId'));
  if (!id) {
    alert('Please log in first!');
    window.location.href = 'login.html';
    return null;
  }
  return id;
}

function formatDate(dateString) {
  if (!dateString) return '-';
  const date = new Date(dateString);
  const day = String(date.getDate()).padStart(2, '0');
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const year = date.getFullYear();
  return `${day}/${month}/${year}`;
}

function formatTime(dateString) {
  if (!dateString) return '-';
  const date = new Date(dateString);
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');
  return `${hours}:${minutes}:${seconds}`;
}

function getStatusClass(status) {
  if (!status) return '';
  const statusLower = status.toLowerCase();
  if (statusLower.includes('pending')) return 'status-pending';
  if (statusLower.includes('paid') || statusLower.includes('complete')) return 'status-paid';
  if (statusLower.includes('cancel')) return 'status-cancelled';
  return '';
}

async function loadOrders() {
  const contentDiv = document.getElementById('orderContent');

  try {
    
    const response = await fetch(`${API_BASE_URL}/orders/${userId}`);

    if (!response.ok) {
      throw new Error(await response.text() || 'Failed to fetch orders');
    }

    const orders = await response.json();

    if (!orders || orders.length === 0) {
      contentDiv.innerHTML = '<div class="empty-state">No orders found</div>';
      return;
    }

    let tableHTML = `
      <table class="order-table">
        <thead>
          <tr>
            <th>Book</th>
            <th>Author</th>
            <th>Time</th>
            <th>Date</th>
            <th>Amount</th>
            <th>Price</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
    `;

    for (const order of orders) {
      // ถ้าไม่มี items
      if (!order.items || order.items.length === 0) {
        tableHTML += `
          <tr>
            <td><em>No items</em></td>
            <td>-</td>
            <td>${formatTime(order.orderDate)}</td>
            <td>${formatDate(order.orderDate)}</td>
            <td>-</td>
            <td>฿${order.total.toFixed(0)}</td>
            <td class="${getStatusClass(order.status)}">
              ${order.status === 'PENDING_PAYMENT' ? 'Pending' : order.status}
            </td>
          </tr>
        `;
      } else {
        // มี items → แสดงทุกเล่ม
        for (const item of order.items) {
          const title =
            item.title ||
            (item.book && item.book.title) ||
            "Book's name";

          const author =
            item.author ||
            item.authorName ||
            (item.book && item.book.author) ||
            "Author's name";

          const quantity = item.quantity || 1;
          const price = item.price ? (item.price * quantity).toFixed(0) : '0';

          tableHTML += `
            <tr>
              <td>${title}</td>
              <td>${author}</td>
              <td>${formatTime(order.orderDate)}</td>
              <td>${formatDate(order.orderDate)}</td>
              <td>${quantity}</td>
              <td>฿${price}</td>
              <td class="${getStatusClass(order.status)}">
                ${order.status === 'PENDING_PAYMENT' ? 'Pending' : order.status}
              </td>
            </tr>
          `;
        }
      }
    }

    tableHTML += `
        </tbody>
      </table>
    `;

    contentDiv.innerHTML = tableHTML;

  } catch (error) {
    console.error('Error loading orders:', error);
    document.getElementById('orderContent').innerHTML = `
      <div class="error">
        ❌ Failed to load orders. Please try again later.
        <br><small>${error.message}</small>
      </div>
    `;
  }
}

window.addEventListener('DOMContentLoaded', loadOrders);
