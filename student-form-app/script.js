const form = document.getElementById("studentForm");
const message = document.getElementById("formMessage");
const preview = document.getElementById("preview");

function showMessage(text, kind) {
  message.textContent = text;
  message.className = `message ${kind}`;
}

function getCheckedSkills() {
  return Array.from(document.querySelectorAll("input[name='skills']:checked")).map((el) => el.value);
}

function validateForm(data) {
  const required = [
    "firstName", "lastName", "email", "phone", "dob",
    "gender", "department", "year", "address", "city", "state", "zip"
  ];

  for (const key of required) {
    if (!data[key]) {
      return `Please fill ${key}.`;
    }
  }

  if (!/^\d{10}$/.test(data.phone)) {
    return "Phone must be a 10-digit number.";
  }

  if (!/^\d{6}$/.test(data.zip)) {
    return "ZIP Code must be a 6-digit number.";
  }

  if (!document.getElementById("terms").checked) {
    return "Please confirm the details checkbox.";
  }

  return "";
}

function renderPreview(data) {
  preview.innerHTML = `
    <h2>Submission Preview</h2>
    <p><strong>Name:</strong> ${data.firstName} ${data.lastName}</p>
    <p><strong>Email:</strong> ${data.email}</p>
    <p><strong>Phone:</strong> ${data.phone}</p>
    <p><strong>DOB:</strong> ${data.dob}</p>
    <p><strong>Gender:</strong> ${data.gender}</p>
    <p><strong>Department:</strong> ${data.department}</p>
    <p><strong>Year:</strong> ${data.year}</p>
    <p><strong>Address:</strong> ${data.address}, ${data.city}, ${data.state} - ${data.zip}</p>
    <p><strong>Skills:</strong> ${data.skills.length ? data.skills.join(", ") : "None selected"}</p>
  `;
  preview.classList.remove("hidden");
}

form.addEventListener("submit", (event) => {
  event.preventDefault();

  const data = Object.fromEntries(new FormData(form).entries());
  data.skills = getCheckedSkills();

  const error = validateForm(data);
  if (error) {
    showMessage(error, "error");
    return;
  }

  showMessage("Student details submitted successfully.", "success");
  renderPreview(data);
});

form.addEventListener("reset", () => {
  showMessage("", "");
  preview.classList.add("hidden");
  preview.innerHTML = "";
});