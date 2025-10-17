
function getUserId() {
  const id = localStorage.getItem('userId');
  if (!id) alert('Please log in first!');
  return id;
}

function toggleDetail(stepDiv) {
  stepDiv.classList.toggle('active');
}

function setBtnLoading(btn, isLoading) {
  if (!btn) return;
  btn.disabled = !!isLoading;
  btn.classList.toggle('is-loading', !!isLoading);
}

document.querySelectorAll('.Step').forEach(stepEl => {
  const detailEl = stepEl.nextElementSibling;
  if (detailEl && detailEl.classList.contains('Detail')) {
    stepEl.addEventListener('click', () => {
      detailEl.classList.toggle('active');
    });
  }
});

const addressForm  = document.getElementById('addressForm');
const paymentForm  = document.getElementById('paymentForm');   
const successMessage = document.getElementById('successMessage');
const backHomeBtn    = document.getElementById('backHomeBtn');
const step1Wrapper  = addressForm?.closest('.Detail');
const step1Header   = step1Wrapper?.previousElementSibling;
const step2Header   = document.querySelectorAll('.Step')[1];
const step2Wrapper  = step2Header?.nextElementSibling;

/* STEP 1 */

addressForm?.addEventListener('submit', async (e) => {
  e.preventDefault();

  const requiredFields = [
    { id: 'fname',     name: 'First name' },
    { id: 'lname',     name: 'Last name' },
    { id: 'telephone', name: 'Phone number' },
    { id: 'address1',  name: 'Address' },
    { id: 'province',  name: 'Province' },
    { id: 'district',  name: 'District' },
    { id: 'postcode',  name: 'Postal code' },
  ];

  let hasError = false;
  let message = '';

  requiredFields.forEach(field => {
    const el = document.getElementById(field.id);
    if (!el || el.value.trim() === '') {
      hasError = true;
      message += `Please fill in ${field.name}\n`;
      el?.classList.add('input-error');
    } else {
      el.classList.remove('input-error');
    }
  });

  const phone = document.getElementById('telephone')?.value.trim();
  if (phone && !/^\d{9,10}$/.test(phone)) {
    hasError = true;
    message += 'Invalid phone number (should be 9–10 digits)\n';
  }

  const postcode = document.getElementById('postcode')?.value.trim();
  if (postcode && !/^\d{5}$/.test(postcode)) {
    hasError = true;
    message += 'Postal code must be 5 digits\n';
  }

  if (hasError) {
    alert('Please check your address details:\n' + message);
    return;
  }

  const payload = {
    firstName:  document.getElementById('fname').value.trim(),
    lastName:   document.getElementById('lname').value.trim(),
    phone:      document.getElementById('telephone').value.trim(),
    address1:   document.getElementById('address1').value.trim(),
    address2:   document.getElementById('address2').value.trim(),
    province:   document.getElementById('province').value.trim(),
    district:   document.getElementById('district').value.trim(),
    postcode:   document.getElementById('postcode').value.trim()
  };

  const userId = getUserId();
  if (!userId) return;

  const submitBtn = addressForm.querySelector('.Confirm-Btn');
  setBtnLoading(submitBtn, true);

  try {
    const res = await fetch(`/api/addresses/add/${userId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      credentials: 'include',
      body: JSON.stringify(payload)
    });

    if (!res.ok) {
      const txt = await res.text();
      throw new Error(txt || `HTTP ${res.status}`);
    }

    step1Wrapper?.classList.remove('active');
    step1Header?.classList.remove('active');
    step2Header?.classList.add('active');
    step2Wrapper?.classList.add('active');


  } catch (err) {
    alert('Failed to save address: ' + err.message);
  } finally {
    setBtnLoading(submitBtn, false);
  }
});

/* STEP 3 */
const fileInput = document.getElementById("slip");
const fileName = document.getElementById("file-name");

fileInput.addEventListener("change", () => {
    if (fileInput.files.length > 0) {
            fileName.textContent = fileInput.files[0].name;
    } else {
        fileName.textContent = "ยังไม่ได้เลือกไฟล์";
    }

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
});


/* FINISH PAGE */

function showFinishPage() {
  document.querySelectorAll('.content').forEach(el => el.style.display = 'none');
  successMessage?.style.setProperty('display', 'flex');
}

backHomeBtn?.addEventListener('click', () => {
  window.location.href = 'index.html';
});




