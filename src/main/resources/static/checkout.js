function toggleDetail(stepDiv) {
    stepDiv.classList.toggle("active");
}
 
document.querySelectorAll('.Step').forEach(stepEl => {
const detailEl = stepEl.nextElementSibling;
if (detailEl && detailEl.classList.contains('Detail')) {
        stepEl.addEventListener('click', () => {
            detailEl.classList.toggle('active'); 
        });
    }
});

const fileInput = document.getElementById("slip");
const fileName = document.getElementById("file-name");

fileInput.addEventListener("change", () => {
    if (fileInput.files.length > 0) {
            fileName.textContent = fileInput.files[0].name;
    } else {
        fileName.textContent = "ยังไม่ได้เลือกไฟล์";
    }
});


const addressForm = document.getElementById("addressForm");
const paymentForm = document.getElementById("paymentForm");
const successMessage = document.getElementById("successMessage");
const backHomeBtn = document.getElementById("backHomeBtn");

addressForm.addEventListener("submit", function(event) {
    event.preventDefault();

    const requiredFields = [
        { id: "fname", name: "ชื่อ" },
        { id: "lname", name: "นามสกุล" },
        { id: "telephone", name: "เบอร์โทรศัพท์" },
        { id: "address1", name: "ที่อยู่" },
        { id: "province", name: "จังหวัด" },
        { id: "district", name: "อำเภอ" },
        { id: "postcode", name: "รหัสไปรษณีย์" },
    ];

    let hasError = false;
    let message = "";

    requiredFields.forEach(field => {
        const el = document.getElementById(field.id);
        if (el.value.trim() === "") {
            hasError = true;
            message += `กรุณากรอก ${field.name}\n`;
            el.classList.add("input-error");
        } else {
            el.classList.remove("input-error");
        }
    });

    const phone = document.getElementById("telephone").value.trim();
    if (phone && !/^\d{9,10}$/.test(phone)) {
        hasError = true;
        message += "เบอร์โทรศัพท์ไม่ถูกต้อง (ควรเป็นตัวเลข 9–10 หลัก)\n";
    }

    const postcode = document.getElementById("postcode").value.trim();
    if (postcode && !/^\d{5}$/.test(postcode)) {
        hasError = true;
        message += "รหัสไปรษณีย์ควรเป็นตัวเลข 5 หลัก\n";
    }

    if (hasError) {
        const fullMessage = "กรุณากรอกที่อยู่ให้ถูกต้อง:\n" + message;
        alert(fullMessage);
        return;
    }

    else{
        const step1Detail = addressForm.closest(".Detail");
        const step1 = step1Detail.previousElementSibling;
        step1Detail.classList.remove("active");
        step1.classList.remove("active");

        const step2 = document.querySelectorAll(".Step")[1];
        const step2Detail = step2.nextElementSibling;
        step2.classList.add("active");
        step2Detail.classList.add("active");
    }
    
});

//button ของ step 2
confirmBtn.addEventListener("click", function(){
    const step2 = document.querySelectorAll(".Step")[1];
    const step2Detail = step2.nextElementSibling;
    step2.classList.remove("active");
    step2Detail.classList.remove("active");

    const step3 = document.querySelectorAll(".Step")[2];
    const step3Detail = step3.nextElementSibling;
    step3.classList.add("active");
    step3Detail.classList.add("active");
});
//

paymentForm.addEventListener("submit", function(event) {
    event.preventDefault();

    const amount = document.getElementById("amount").value.trim();
    const date = document.getElementById("date").value.trim();
    const time = document.getElementById("time").value.trim();
    const slip = document.getElementById("slip").files.length;

    let selectedBank = false;
    document.querySelectorAll('input[name="bank"]').forEach(radio => {
        if (radio.checked) selectedBank = true;
    });
    
    let message = "";
    if (!selectedBank) message += "กรุณาเลือกธนาคาร\n";
    if (amount === "") message += "กรุณากรอกยอดเงิน\n";
    if (date === "") message += "กรุณาเลือกวันที่โอน\n";
    if (time === "") message += "กรุณาเลือกเวลาโอน\n";
    if (slip === 0) message += "กรุณาแนบสลิป\n";

    if (message) {
        const fullMessage = "กรุณากรอกรายละเอียดการชำระเงินให้ครบ:\n" + message;
        alert(fullMessage);
        return;
    }
    
    document.querySelectorAll(".content").forEach(d => d.style.display = "none");
    successMessage.style.display = "flex";
    addressForm.reset();
    paymentForm.reset();
}); 
backHomeBtn.addEventListener("click", () => {window.location.href = "index.html";});