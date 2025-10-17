const API_BASE = 'http://localhost:8080';

function getUserId() {
  const id = localStorage.getItem('userId');
  if (!id) {
    alert('Please log in first!');
    window.location.href = 'login.html';
  }
  return id;
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
      stepEl.classList.toggle('active');
    });
  }
});

const addressForm    = document.getElementById('addressForm');
const paymentForm    = document.getElementById('paymentForm');
const confirmBtn     = document.getElementById('confirmBtn');
const successMessage = document.getElementById('successMessage');
const backHomeBtn    = document.getElementById('backHomeBtn');

const step1Wrapper = addressForm?.closest('.Detail');
const step1Header  = step1Wrapper?.previousElementSibling;
const step2Header  = document.querySelectorAll('.Step')[1];
const step2Wrapper = step2Header?.nextElementSibling;

/* - STEP 1 - */
addressForm?.addEventListener('submit', async (e) => {
  e.preventDefault();

  const required = [
    { id: 'fname',     label: 'First name' },
    { id: 'lname',     label: 'Last name' },
    { id: 'telephone', label: 'Phone number' },
    { id: 'address1',  label: 'Address' },
    { id: 'province',  label: 'Province' },
    { id: 'district',  label: 'District' },
    { id: 'postcode',  label: 'Postal code' },
  ];

  let hasError = false, msg = '';
  required.forEach(f => {
    const el = document.getElementById(f.id);
    if (!el || el.value.trim() === '') {
      hasError = true; msg += `Please fill in ${f.label}\n`;
      el?.classList.add('input-error');
    } else el.classList.remove('input-error');
  });

  const phone = document.getElementById('telephone')?.value.trim();
  if (phone && !/^\d{9,10}$/.test(phone)) { hasError = true; msg += 'Invalid phone (9–10 digits)\n'; }
  const postcode = document.getElementById('postcode')?.value.trim();
  if (postcode && !/^\d{5}$/.test(postcode)) { hasError = true; msg += 'Postal code must be 5 digits\n'; }
  if (hasError) { alert('Please check your address:\n' + msg); return; }

  const userId = getUserId();
  if (!userId) return;

  const payload = {
    userId:    Number(userId),
    name:      document.getElementById('fname').value.trim(),
    surname:   document.getElementById('lname').value.trim(),
    telephone: document.getElementById('telephone').value.trim(),
    address1:  document.getElementById('address1').value.trim(),
    address2:  document.getElementById('address2').value.trim(),
    province:  document.getElementById('province').value.trim(),
    district:  document.getElementById('district').value.trim(),
    postcode:  document.getElementById('postcode').value.trim()
  };

  const submitBtn = addressForm.querySelector('.Confirm-Btn');
  setBtnLoading(submitBtn, true);

  try {
    const res = await fetch(`${API_BASE}/api/addresses`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!res.ok) throw new Error(await res.text() || `HTTP ${res.status}`);

    const saved = await res.json();
    localStorage.setItem('checkout_address_id', String(saved.id));

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

/* - STEP 2 (mock) - */
confirmBtn?.addEventListener('click', async () => {
  const userId    = getUserId();
  const addressId = Number(localStorage.getItem('checkout_address_id'));
  if (!userId) return;
  if (!addressId) { alert('ยังไม่มีที่อยู่จัดส่ง โปรดกรอก Step 1 ก่อน'); return; }

  try {
    const res = await fetch(`${API_BASE}/api/orders/checkout/${userId}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ addressId })
    });
    if (!res.ok) throw new Error(await res.text() || `HTTP ${res.status}`);

    const step2 = document.querySelectorAll('.Step')[1];
    const step2Detail = step2.nextElementSibling;
    step2.classList.remove('active');
    step2Detail.classList.remove('active');

    const step3 = document.querySelectorAll('.Step')[2];
    const step3Detail = step3.nextElementSibling;
    step3.classList.add('active');
    step3Detail.classList.add('active');

  } catch (err) {
    alert('Checkout failed: ' + err.message);
  }
});

/* - STEP 3 (mock) - */
const fileInput = document.getElementById('slip');
const fileName  = document.getElementById('file-name');

fileInput?.addEventListener('change', () => {
  fileName.textContent = fileInput.files.length ? fileInput.files[0].name : 'ยังไม่ได้เลือกไฟล์';
});

paymentForm?.addEventListener('submit', (event) => {
  event.preventDefault();

  const amount = document.getElementById('amount').value.trim();
  const date   = document.getElementById('date').value.trim();
  const time   = document.getElementById('time').value.trim();
  const slip   = document.getElementById('slip').files.length;

  let selectedBank = false;
  document.querySelectorAll('input[name="bank"]').forEach(r => { if (r.checked) selectedBank = true; });

  let message = '';
  if (!selectedBank) message += 'กรุณาเลือกธนาคาร\n';
  if (!amount)       message += 'กรุณากรอกยอดเงิน\n';
  if (!date)         message += 'กรุณาเลือกวันที่โอน\n';
  if (!time)         message += 'กรุณาเลือกเวลาโอน\n';
  if (!slip)         message += 'กรุณาแนบสลิป\n';

  if (message) { alert('กรุณากรอกรายละเอียดการชำระเงินให้ครบ:\n' + message); return; }

  document.querySelectorAll('.content').forEach(d => d.style.display = 'none');
  document.querySelectorAll('footer').forEach(d => d.style.display = 'none');
  successMessage.style.display = 'flex';
  addressForm.reset();
  paymentForm.reset();
});

/* - Finish - */
backHomeBtn?.addEventListener('click', () => {
  window.location.href = 'index.html';
});