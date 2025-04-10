const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
console.log("Connecting to the completed");

function formatDate(dateString) {
	let date = new Date(dateString);
	let day = String(date.getDate()).padStart(2, '0');
	let month = String(date.getMonth() + 1).padStart(2, '0');
	let year = String(date.getFullYear());
	return `${day}-${month}-${year}`;
}



$(document).ready(function() {
	$.ajax({
		url: "/getFillForm",
		type: "GET",
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(data) {
			console.log("Data received:", data);

			let table = $("#form_datatable").DataTable();
			table.clear().destroy();

			let tableBody = $("#form_datatable tbody");
			tableBody.empty();

			$.each(data, function(index, form) {
				let formattedDate = formatDate(form.completedDate);
				let formId = "FORM-" + (form.formId ? form.formId.toString().padStart(2, '0') : "N/A");
				let row = `<tr>
        <td>${formattedDate}</td>
        <td>${formId}</td>
        <td>${form.formName}</td>
        <td>${form.Username}</td>
        <td class="text-center">
            <span data-toggle="modal" data-target="#all_question_preview">
                <a href="javascript:void(0)" class="text-info fa-size preview-form" data-form-id="${form.formId || 'N/A'}">
                    <i class="fa fa-eye"></i>
                </a>
            </span>
        </td>
    </tr>`;
				tableBody.append(row);
			});
			$("#form_datatable").DataTable({
				destroy: true,
				scrollX: true,
				"bAutoWidth": true,
				paging: true,
				"bLengthChange": false,
				"columnDefs": [{
					"targets": 2,
					"orderable": false
				}],
				"pageLength": 10,
				fixedColumns: {
					rightColumns: 1,
					leftColumns: 0
				},
				language: {
					paginate: {
						next: '<i class="fa fa-angle-double-right"></i>',
						previous: '<i class="fa fa-angle-double-left"></i>'
					}
				},
				"dom": '<"top"pif>rt<"clear">'
			});
		},
		error: function(xhr, status, error) {
			console.error("Error fetching data:", xhr.responseText);
		}
	});
});

//to preview complete form
$(document).on("click", ".preview-form", function() {
	let formId = $(this).data("form-id");

	$.ajax({
		url: `/getFormDetails/${formId}`,
		type: "GET",
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(response) {
			console.log(response)
			if (response) {
				populateModal(response);
				$('#all_question_preview').modal('show');
			}
		},
		error: function() {
			alert("Failed to fetch form details.");
		},
	});
});

function populateModal(data) {
	$("#all_question_preview .modal-body").empty();

	let modalContent = `
        <div class="card mb-2 queshadow">
            <div class="card-body">
                <div class="row pr-3 pl-3">
                    <div class="col-xl-4 col-lg-4 col-sm-4 colmspadding">
                        <p class="compact mb-1"><span class="font-weight-700">Completed Date:</span>
                            <span class="displayblock font-medium-2">${formatDate(data.completedDate)}</span>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div class="detailsbg">
            <div class="row pr-2 pl-2">
                <div class="col-xl-12 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                    <p class="mb-1 font-weight-600"><span class="font-weight-700">Form Title:</span>
                        <span>${data.formName}</span>
                    </p>
                    <p class="mb-0 font-weight-600"><span class="font-weight-700">Description:</span>
                        <span>${data.description}</span>
                    </p>
                </div>
            </div>
        </div>`;

	data.questions.forEach((question, index) => {
		modalContent += `
        <div class="card mb-2 queshadow">
            <div class="card-body">
                <div class="row pl-2 pr-2">
                    <div class="col-xl-1 col-lg-1 col-sm-2 colmspadding">
                        <span class="question">Q : ${index + 1}</span>
                    </div>
                    <div class="col-xl-11 col-lg-11 col-sm-10 colmspadding">
                        <div class="form-group mb-0 text-justify">
                            <p class="font-weight-700 mb-1"><span class="text-danger">*</span> ${question.title}</p>
                            <p class="mb-1">${question.description}</p>
                        </div>
                        <div class="form-group mb-0">
                            <div class="row pl-2 pr-2">
                                <div class="col-xl-12 col-lg-12 col-sm-12 colmspadding">
                                    <p class="font-weight-700 mb-1 text-justify">Answer</p>
                                    <p class="mb-1 text-justify">${question.answer}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>`;
	});

	$("#all_question_preview .modal-body").html(modalContent);
}









