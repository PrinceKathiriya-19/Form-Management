const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
console.log("Connected to the JS fill form");

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

let formdetails = {};
$(document).ready(function() {
	
	$.ajax({
		url: "/unsubmitted",
		type: "GET",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(data) {
			let dropdown = $(".selectpicker");
			dropdown.empty();
			dropdown.append('<option value="">Select</option>');

			data.forEach(form => {
				formdetails[form.formId] = {
					title: form.formTitle,
					description: form.formText
				};
				dropdown.append(`<option value="${form.formId}">${form.formTitle}</option>`);
			});

			dropdown.selectpicker("refresh");
		},
		error: function() {
			console.error("Error fetching forms.");
		}
	});

	$("#searchbtn").off("click").on("click", function() {
		let formId = $(".selectpicker").val();
		if (!formId) {
			showErrorToaster("Please select a form.");
			return;
		}

		$(".showformfill .card-body").empty();

		$.ajax({
			url: `/${formId}/questions`,
			type: "GET",
			xhrFields: {
				withCredentials: true
			},
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success: function(data) {
				console.log("Fetched data:", data);

				$(".showformfill").show();

				let formTitle = formdetails[formId].title;
				let formText = formdetails[formId].description;

				$(".showformfill .card-body").append(`
                <div class="detailsbg mb-3">
                    <div class="row pr-2 pl-2">
                        <div class="col-xl-12 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                            <p class="mb-1 font-weight-600">
                                <span class="font-weight-700">Form Title:</span> <span>${formTitle}</span>
                            </p>
                            <p class="mb-0 font-weight-600">
                                <span class="font-weight-700">Description:</span> <span>${formText}</span>
                            </p>
                        </div>
                    </div>
                </div>
            `);

				if (data.length === 0) {
					$(".showformfill .card-body").append('<p class="text-muted">No questions available for this form.</p>');
					return;
				}

				$.each(data, function(index, question) {
					$(".showformfill .card-body").append(`
                    <div class="card mb-2 queshadow">
                        <div class="question-container">
                            <div class="row pl-2 pr-2">
                                <div class="col-xl-1 col-lg-1 col-sm-2">
                                    <span class="question">Q : ${index + 1}</span>
                                </div>
                                <div class="col-xl-11 col-lg-11 col-sm-10">
                                    <p class="font-weight-700 mb-1">
                                    ${question.questionRequired ? '<span class="text-danger">*</span>' : ""}
                                    ${question.questionName}
                                    </p>
                                    <p class="mb-1">${question.questionDescription}</p>
                                    <div class="form-group mb-0">
                                        ${generateAnswerOptions(question, index)}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                `);
				});

				$(".showformfill .card-body").append(`
                <div class="card mb-0 queshadow">
                    <div class="card-body">
                        <div class="row pl-2 pr-2 text-center">
                            <div class="col-xl-12 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                                <a id="submitFormBtn" class="btn btn-success btn-padding mr-2"><i class="fa fa-floppy-o mr-2"></i>Submit</a>
                                <a class="btn btn-success btn-padding mr-2"><i class="fa fa-print mr-2"></i>Print</a>
                                <a id="cancel" class="btn btn-success btn-padding"><i class="fa fa-times mr-2"></i>Cancel</a>
                            </div>
                        </div>
                    </div>
                </div>
            `);
			},
			error: function() {
				alert("Error fetching form questions.");
			}
		});
	});
});

function generateAnswerOptions(question, index) {
	let { answerType, choices, questionId } = question;
	let questionName = `question_${questionId}`;
	let inputHtml = "";

	if (answerType === "Single Choice" && choices.length > 0) {

		inputHtml = `
            <div class="form-check form-check-inline">
                ${choices.map((c, i) => `
                    <input type="radio" id="${questionName}_${index}_${i}" 
                        name="${questionName}" 
                        class="form-check-input" value="${c}">
                    <label class="form-check-label pr-3" for="${questionName}_${index}_${i}">${c}</label>
                `).join("")}
            </div>
        `;
	} else if (answerType === "Multiple Choice" && choices.length > 0) {

		inputHtml = `
            <div class="form-check form-check-inline">
                ${choices.map((c, i) => `
                    <input type="checkbox" id="${questionName}_${index}_${i}" 
                        name="${questionName}[]" 
                        class="form-check-input" value="${c}">
                    <label class="form-check-label pr-3" for="${questionName}_${index}_${i}">${c}</label>
                `).join("")}
            </div>
        `;
	} else if (answerType === "Single Select Dropdown" && choices.length > 0) {

		inputHtml = `
            <select class="form-control" name="${questionName}">
                <option value="">Select</option>
                ${choices.map(c => `<option value="${c}">${c}</option>`).join("")}
            </select>
        `;
	} else if (answerType === "Multi Select Dropdown" && choices.length > 0) {

		inputHtml = `
            <select class="selectpicker" multiple data-selected-text-format="count"
                data-style="btn-light bg-transfer" data-actions-box="true"
                name="${questionName}" data-live-search="true">
                ${choices.map(c => `<option value="${c}">${c}</option>`).join("")}
            </select>
        `;
		setTimeout(() => {
			$(".selectpicker").selectpicker("refresh");
		}, 1000);
	}
	else if (answerType === "Single Textbox") {
		inputHtml = `<input type="text" class="form-control" name="${questionName}" placeholder="Enter Your Answer">`;
	} else if (answerType === "Multiline Textbox") {
		inputHtml = `<textarea class="form-control textareasize" name="${questionName}" placeholder="Enter Your Answer"></textarea>`;
	} else if (answerType === "Date") {
		inputHtml = `
            <div class="col-xl-3 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                <div class="input-group date">
                    <input type="text" class="form-control datepicker" placeholder="dd/mm/yyyy" 
                        name="${questionName}">
                    <span class="input-group-addon inputgroups">
                        <i class="mdi mdi-calendar"></i>
                    </span>
                </div>
            </div>
        `;
	} else {
		inputHtml = "<p class='text-muted'>No input required.</p>";
	}

	return inputHtml;
}

//for the reset button
$(document).ready(function() {
	$(".show-details-table").off("click").on("click", function() {
		$(".showformfill").hide();
		$(".selectpicker").val("").selectpicker("refresh");
		$(".showformfill .card-body").empty();
	});
});

//for cancel
$(document).on("click", "#cancel", function() {
	alert("lel")
	$(".showformfill").hide();
	$(".selectpicker").val("").selectpicker("refresh");
	$(".showformfill .card-body").empty();
});

$(document).on("click", "#submitFormBtn", function() {
	let formData = [];
	let isValid = true;

	let formId = Number($(".selectpicker").val());

	$(".question-container").each(function(index) {
		let inputField = $(this).find("input, select, textarea");
		let questionIdAttr = inputField.attr("name");
		let questionId = parseInt(questionIdAttr.split("_")[1], 10); // Convert to number
		let questionRequired = $(this).find(".text-danger").length > 0;


		let answer = $(this).find("input[type='checkbox']:checked")
			.map(function() {
				return $(this).val();
			})
			.get()
			.join(", ") ||
			$(this).find("input[type='radio']:checked").val() ||
			$(this).find("input[type='text'], textarea, select").val();

		if (questionRequired && (!answer)) {
			isValid = false;
			$(this).find(".form-group").append('<p class="text-danger error">This field is required.</p>');
		} else {
			$(this).find(".error").remove();
		}
		let formName = $(".selectpicker option:selected").first().text().trim();


		let filldata = {
			formId: formId,
			formName: formName,
			questionId: questionId,
			answer: answer,
			isSubmitted: true
		};

		//console.log(" Adding entry:", filldata);
		formData.push(filldata);
	});
	if (!isValid) {
		showErrorToaster("Please fill all required fields before submitting.");
		return;
	}

	console.log("Final Data Before Sending:", JSON.stringify(formData, null, 2));


	if (formData.length === 0) {
		showErrorToaster("No answers provided.");
		return;
	}

	$.ajax({
		url: "/submit-fill-form",
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(formData),
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(response) {
			//console.log(response);
			showSuccessToaster("Form submitted successfully!");
			setTimeout(() => {
				location.reload();
			}, 2000)
			$(".showformfill").hide();
			$(".question-container input[type='text'], .question-container textarea").val("");
			$(".question-container input[type='checkbox'], .question-container input[type='radio']").prop("checked", false);
			$(".selectpicker").val("0").change();
		},
		error: function(error) {
			showErrorToaster("Error submitting form.");
		}
	});
});
$(document).on("focus",".datepicker", function() {
	$(this).datepicker({
		autoclose: true,
		todayHighlight: true,
		format: "dd/mm/yyyy",
		clearBtn: true
	}).datepicker("show");
});