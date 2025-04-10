const fname = $("#fname");
const lname = $("#lname");
const email = $("#email");
const contact = $("#contact");
const gender = $("#gender");
const validFrom = $("#valid_from");
const validTo = $("#valid_to");
const role = $("#role");
const save = $("#save");
const fileInput = $("input[name='file']");
const formContainer = $("#portfolio_add_detail");
//const userImg=$("#userimg");
//const add_edit_userimg=$("#add_edit_userimg");
//const removeImgBtn=$("#removeImgBtn");
//const createdBy = 1;
let userId = null;
//const add=$("#addbutton");
const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");





//success Toaster
function showSuccessToaster(message) {
	$.toast({
		heading: 'Success!',
		text: message,
		position: 'top-right',
		loaderBg: '#5ba035',
		icon: 'success',
		hideAfter: 2000,
		stack: 1
	})
}

//Error Toaster
function showErrorToaster(message) {
	$.toast({
		heading: 'Error!',
		text: message,
		position: 'top-right',
		loaderBg: '#ff3547',
		icon: 'error',
		hideAfter: 2000,
		stack: 1
	})
}
//Email validation function
function isValidEmail(email) {
	const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	return emailRegex.test(email);
}
function validation() {
	if (fname.val().trim() === "" || fname.val().length < 2) {
		showErrorToaster("First Name is Required");
		return false;
	}

	if (lname.val().trim() === "" || lname.val().length < 2) {
		showErrorToaster("Last Name is Required");
		return false;
	}

	if (email.val().trim() === "" || !isValidEmail(email.val())) {
		showErrorToaster("Valid Email is required");
		return false;
	}

	if (contact.val().trim() === "") {
		showErrorToaster("Contact Number is required");
		return false;
	}
	if (contact.val().length !== 10) {
		showErrorToaster("Contact Number must be exactly 10 digits");
		return false;
	}


	if ($("#gender").val().trim() === "") {
		showErrorToaster("Gender is required");
		return false;
	}

	if (validFrom.val() === "") {
		showErrorToaster("Valid From is required");
		return false;
	}

	if (validTo.val() === "") {
		showErrorToaster("Valid To is required");
		return false;
	}

	if ($("#role").val().trim() === "") {
		showErrorToaster("Role is required");
		return false;
	}

	let isEditMode = userId !== undefined && userId !== null && userId !== "";
	if (!isEditMode && fileInput[0].files.length === 0) {
		showErrorToaster("Image upload is required.");
		return false;
	}

	return true;
}

//contact input validation
contact.on("input", function() {
	let inputVal = $(this).val().replace(/\D/g, "");
	if (inputVal.length > 10) {
		inputVal = inputVal.substring(0, 10);
	}
	$(this).val(inputVal);
});

//first name input validation
fname.on("input", function() {
	const fnameregex = this.value;
	this.value = fnameregex.replace(/[^a-zA-Z]/g, '');
	if (this.value.length > 20) {
		this.value = this.value.slice(0, 20);
	}

});

//last name input validation
lname.on("input", function() {
	const lnameregex = this.value;
	this.value = lnameregex.replace(/[^a-zA-Z]/g, '');
	if (this.value.length > 20) {
		this.value = this.value.slice(0, 20);
	}

})

validFrom.on("input", function() {
	let value = $(this).val();
	if (!/^\d{4}-\d{2}-\d{2}$/.test(value)) {
		$(this).val("");
		showErrorToaster("Please enter a valid date.");
	}
});

validTo.on("input", function() {
	let value = $(this).val();
	if (!/^\d{4}-\d{2}-\d{2}$/.test(value)) {
		$(this).val(""); // Clear invalid input
		showErrorToaster("Please enter a valid date.");
	}
});

$(document).ready(function() {
	const userImg = $("#userimg");
	const add_edit_userimg = $("#add_edit_userimg");
	const removeImgBtn = $("#removeImgBtn");
	const add = $("#addbutton");

	add.on("click", () => {
		add_edit_userimg.attr("src", "assets/images/users/default_user.png");

	});

	userImg.on("change", function(event) {
		const file = event.target.files[0];
		if (file) {
			const reader = new FileReader();
			reader.onload = function(e) {
				add_edit_userimg.attr("src", e.target.result);
			};
			reader.readAsDataURL(file);
		} else {
			add_edit_userimg.attr("src", "assets/images/users/default_user.png");
		}
	});


	removeImgBtn.on("click", function() {
		userImg.val(""); 
		add_edit_userimg.attr("src", "assets/images/users/default_user.png");
	});
});



save.on("click", function(e) {
	e.preventDefault();

	if (!validation()) return;

	const formData = new FormData();
	formData.append("fname", fname.val());
	formData.append("lname", lname.val());
	formData.append("email", email.val());
	formData.append("contact", contact.val());
	formData.append("gender", $("#gender option:selected").text());
	formData.append("validFrom", validFrom.val());
	formData.append("validTo", validTo.val());
	formData.append("role", $("#role option:selected").text());
	formData.append("active", "YES");
	formData.append("image", fileInput[0].files[0]);

	let url = "/saveuser";
	let method = "POST";

	if (userId) {
		url = `/updateuser/${userId}`;
		method = "PUT";
		formData.append("userId", userId);
	}

	console.log("CSRF Token:", csrfToken);
	console.log("CSRF Header:", csrfHeader);
	$.ajax({
		url: url,
		type: method,
		data: formData,
		processData: false,
		contentType: false,
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function() {
			showSuccessToaster(userId ? "User updated successfully!" : "User added successfully!");
			setTimeout(() => location.reload(), 2000);
		},
		error: function(xhr, error) {
			showErrorToaster("Error: " + xhr.responseText);
			console.log(error.responseText);
		}
	});
});

$(document).ready(function() {
	$.ajax({
		url: "/displaygrid",
		type: "GET",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(response) {
			let tableBody = $("#users_datatable tbody");
			tableBody.empty();

			//console.log(response); 

			response.forEach(user => {
				let imageUrl = user.userImage ? `/userImages/${user.userImage}` : "/userImages/default.png";

				let userRow = `
            <tr>
                <td class="text-center">
                    <h2 class="table-avatar">
                         <a href="javascript:void(0)" 
                           data-toggle="popover" 
                           data-trigger="hover" 
                           data-html="true" 
                           data-placement="right" 
                           data-template="<div class='popover fade bs-popover-right' role='tooltip'>
                                            <div class='arrow'></div>
                                            <h3 class='popover-header p-0 border_radius6'></h3>
                                        </div>" 
                           data-title="<img src='${imageUrl}' width='150' height='150' class='border_radius6'>">
                            <img src="${imageUrl}" alt="user image" class="img-radius avatar">
                        </a>
                        <span>${user.userName}</span>
                    </h2>
                </td>
                <td>${user.userEmail}</td>
                <td>${user.userContact}</td>
                <td>${user.userValidFrom}</td>
                <td>${user.userValidTo}</td>
                <td>${user.userGender}</td>
                <td>${user.userRole}</td>
                <td>${user.active}</td>
                <td class="text-center">
                  <a href="javascript:void(0)" class="text-success fa-size client_add_btn"
               onclick="openEditModal('${user.userId}', '${user.userName}', '${user.userEmail}', '${user.userContact}', '${user.userGender}', '${user.userValidFrom}', '${user.userValidTo}', '${user.userRole}','${imageUrl}')">
                <i class="fa fa-pencil"></i>
            </a>
                            <span class="delete-user-alert">
                                <a href="javascript:void(0)" class="text-danger fa-size"
                                   onclick="deleteUser('${user.userId}')">
                                    <i class="fa fa-trash"></i>
                                </a>
                    </span>
                </td>
            </tr>
        `;
				tableBody.append(userRow);
			});
			$('[data-toggle="tooltip"]').tooltip();
			$('[data-toggle="popover"]').popover();

			$('.delete-user-alert').on('click', function() {
				$.confirm({
					title: 'Delete Record..!',
					content: 'Please be sure before deleting record',
					theme: 'material',
					icon: 'fa fa-warning',
					type: 'red',
					buttons: {
						omg: {
							text: 'Delete',
							btnClass: 'btn-red',
						},
						close: function() {
						}
					}
				});
			});

			$('#users_datatable').DataTable({
				destroy: true,
				scrollX: true,
				"bAutoWidth": true,
				paging: true,
				"bLengthChange": false,
				"columnDefs": [{
					"targets": [5],
					"orderable": false
				}],
				"pageLength": 10,
				fixedColumns: {
					rightColumns: 1,
					leftColumns: 0
				},
				language: {
					paginate: {
						next: '<i class="fa fa-angle-double-right">',
						previous: '<i class="fa fa-angle-double-left">'
					}
				},
				"dom": '<"top"pif>rt<"clear">'
			});

			$("#searchbtn").click(function() {
				$("#searchcollapse").trigger('click');
			});
		},

		error: function(xhr, status, error) {
			console.log("Error fetching users:", xhr.responseText);
		}
	});
});

function openEditModal(id, name, email, contact, gender, validFrom, validTo, role, imageUrl) {
	console.log("id:" + id)
	/*console.log(gender);
	console.log(role)*/

	if (!id || id === "undefined") {
		console.error("Invalid ID for editing:", id);
		showErrorToaster("Invalid user ID");
		return;
	}
	userId = id;
	document.getElementById("fname").value = name.split(" ")[0];
	document.getElementById("lname").value = name.split(" ")[1] || "";
	document.getElementById("email").value = email;
	document.getElementById("contact").value = contact;
	$("#gender").val(gender).change();
	document.getElementById("valid_from").value = validFrom;
	document.getElementById("valid_to").value = validTo;
	$("#role").val(role).change();

	if (imageUrl && imageUrl !== "null" && imageUrl !== "") {
		$("#add_edit_userimg").attr("src", imageUrl);
	} else {
		$("#add_edit_userimg").attr("src", "assets/images/users/default_user.png");
	}

	$("#addUserModal").modal("show");
}


function openAddUserModal() {
	editingUserId = null;
	$("#userForm")[0].reset();
	$("#addUserModal").modal("show");
}

function deleteUser(userId) {
	console.log("Delete clicked for User ID:", userId);
	$.ajax({
		url: `/deleteuser/${userId}`,
		type: "DELETE",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(response) {
			showSuccessToaster("User deleted successfully!");
			setTimeout(() => location.reload(), 2000);
		},
		error: function(xhr) {
			showErrorToaster("Error deleting user: " + xhr.responseText);
		}
	});
}

//to move back button
let moveback = $("#moveBack");
moveback.on("click", function() {
	//alert("oooo")
	fname.val("");
	lname.val("");
	email.val("");
	contact.val("");
	validFrom.val("");
	validTo.val("");
	 $("#gender").val(" ").selectpicker("refresh"); 
    $("#role").val(" ").selectpicker("refresh"); 
	
});


//to cancel button
let cancel = $("#cancel");
cancel.on("click", function() {
	//alert("oooo")
	fname.val("");
	lname.val("");
	email.val("");
	contact.val("");
	validFrom.val("");
	validTo.val("");
	 $("#gender").val(" ").selectpicker("refresh");  
    $("#role").val(" ").selectpicker("refresh"); 
	
});















