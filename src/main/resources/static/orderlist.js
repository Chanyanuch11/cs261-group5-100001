document.addEventListener("DOMContentLoaded", () => {
    console.log("admin-order&slip.js loaded");

    // --------------------------------
    // 0) หา table ก่อน ถ้าไม่เจอให้จบเลย
    // --------------------------------
    const table = document.querySelector(".order-table");
    if (!table) {
        console.warn("No .order-table found on this page");
        return;
    }

    // --------------------------------
    // 1) (option) เช็กสิทธิ์ Admin – ไว้เติมทีหลังได้
    // --------------------------------
    const email = localStorage.getItem("email");
   if (!email || email.toLowerCase() !== "admin@admin.com") {
         alert("Access denied");
       window.location.href = "index.html";
        return;
    }

    // --------------------------------
    // 2) ตัวแปรเก็บรายการที่อนุมัติ/ปฏิเสธไว้ก่อน (ยังไม่ส่ง backend)
    // --------------------------------
    const approvedOrders = [];
    const rejectedOrders = [];

    // --------------------------------
    // 3) Event: คลิกปุ่ม ✔ หรือ ✖ ภายในตาราง
    //    ใช้ event delegation ติดที่ table แทน document
    // --------------------------------
    table.addEventListener("click", (event) => {
        // หาว่าคลิกโดน element ที่มี class="icon" ไหม
        const btn = event.target.closest(".icon");
        if (!btn || !table.contains(btn)) {
            return; // ถ้าไม่ใช่ปุ่มในตารางก็ไม่ทำอะไร
        }

        const row = btn.closest("tr");
        if (!row) return;

        const cells = row.querySelectorAll("td");
        if (cells.length < 7) {
            console.warn("Row format is not as expected:", row);
            return;
        }

        // --------------------------------
        // 4) ดึงข้อมูลของแถวที่คลิก
        // --------------------------------
        const order = {
            id: row.dataset.orderId || null,
            username: cells[1].textContent.trim(),
            book: cells[2].textContent.trim(),
            time: cells[3].textContent.trim(),
            date: cells[4].textContent.trim(),
            amount: parseInt(cells[5].textContent.trim(), 10) || 0,
            price: cells[6].textContent.trim()
        };

        // --------------------------------
        // 5) ถ้ากด ✔ = อนุมัติ
        // --------------------------------
        if (btn.classList.contains("check")) {
            row.classList.add("approved");
            row.classList.remove("rejected");

            approvedOrders.push(order);
            console.log("✅ Approved:", order);

         
        }

        // --------------------------------
        // 6) ถ้ากด ✖ = ปฏิเสธ
        // --------------------------------
        else if (btn.classList.contains("cross")) {
            row.classList.add("rejected");
            row.classList.remove("approved");

            rejectedOrders.push(order);
            console.log("❌ Rejected:", order);

           
        }
    });
});