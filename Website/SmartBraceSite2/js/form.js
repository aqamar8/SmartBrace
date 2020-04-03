function checkName () {
            var nameBox = document.getElementById("name");
            var name = nameBox.value;
            if (name.length < 5) {
                // Name is too short.
                nameBox.style.borderColor = "red";
            } else {
                // Name is long enough.
                nameBox.style.borderColor = "gray";
            }
        }
        function checkEmail() {
            var emailBox = document.getElementById("email");
            var email = emailBox.value;
            if (email.length < 8) {
                emailBox.style.borderColor = "red";
            } else {
                emailBox.style.borderColor = "gray";
            }
        }
        function checkPhone() {
            var phoneBox = document.getElementById("phone");
            var phone = phoneBox.value;
            if (isNaN (phone) || phone.length < 10 || phone.length > 10) {
                phoneBox.style.borderColor = "red";
            } else {
                phoneBox.style.borderColor = "gray";
            }
        }
        function selectAll() {
            var isChecked = document.getElementById("all").checked;
            var checkboxes = document.getElementsByName("interests");
            if (isChecked == true) {
                for (var i = 0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = true;
                }
            } else {
                for (var i=0; i < checkboxes.length; i++) {
                    checkboxes[i].checked = false;
                }
            }
        }
       function updateStatus()
{
    if (document.getElementById("canada").checked)
        document.getElementById("canadaCountry").style.display = "block";                                       
    else
        document.getElementById("canadaCountry").style.display = "none";
    
    if (document.getElementById("us").checked)
        document.getElementById("usCountry").style.display = "block";                                       
    else
        document.getElementById("usCountry").style.display = "none";
}