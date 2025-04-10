const formid = $("#form");
const title = $("#title");
const alias = $("#alias");
const module = $("#moduleDropdown");
const Characteristic = $("#charactersticDrop");
const SubCharacteristic = $("#SubcharactersticDrop");
const Recurrence = $("#Recurrence");
const Month = $("#month");
const Period = $("#period");
const EffectiveDate = $("#date_from");
const active = $("#active");
const text = $("#text");
const addform = $("#portfolio_add_detail")
const questionLabel = $("#questionLabel")
const questionName = $("#questionName")
const description = $("#description")
const answertype = $("#answer")
const requiredAnswer = $("#reqans")
const saveQuestion = $("#saveQuestion")
const saveMain = $("#savemain");
const csrfToken = $("meta[name='_csrf']").attr("content");
const csrfHeader = $("meta[name='_csrf_header']").attr("content");
console.log(csrfToken)
console.log(csrfHeader)

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

function validation() {

	if (title.val().trim() === "" || title.val().length < 2) {
		showErrorToaster("Title is Required and Must be more than 2 letter");
		return false;
	}

	if (alias.val().trim() === "" || alias.val().length < 2) {
		showErrorToaster("alias is Required and Must be more than 2 letter");
		return false;
	}

	if (module.val() === "") {
		showErrorToaster("module is required");
		return false;
	}

	if (Characteristic.val() === "") {
		showErrorToaster("Characteristic is required");
		return false;
	}

	if (SubCharacteristic.val() === "") {
		showErrorToaster("SubCharacteristic is required");
		return false;
	}

	if (Recurrence.val() === "") {
		showErrorToaster("Recurrence is required");
		return false;
	}

	if (Month.val() === "") {
		showErrorToaster("Month is required");
		return false;
	}

	if (Period.val() === "") {
		showErrorToaster("period is required");
		return false;
	}

	if (EffectiveDate.val() === "") {
		showErrorToaster("EfectiveDate is required");
		return false;
	}

	if (text.val() === "") {
		showErrorToaster("Text is required");
		return false;
	}

	return true;

}

//first name input validation
title.on("input", function() {
	const fnameregex = this.value;
	this.value = fnameregex.replace(/[^a-zA-Z0-9\s!@#$%^&*(),.?":{}|<>_-]/g, '');
	if (this.value.length > 50) {
		this.value = this.value.slice(0, 50);
	}

});

alias.on("input", function() {
	const fnameregex = this.value;
	this.value = fnameregex.replace(/[^a-zA-Z0-9\s!@#$%^&*(),.?":{}|<>_-]/g, '');
	if (this.value.length > 20) {
		this.value = this.value.slice(0, 20);
	}

});



Period.on("input", function() {
	let inputVal = $(this).val().replace(/\D/g, "");
	if (inputVal.length > 2) {
		inputVal = inputVal.substring(0, 2);
	}
	$(this).val(inputVal);
});

text.on("input", function() {
	if (this.value.length > 150) {
		this.value = this.value.slice(0, 150);
	}

});

//for the module dropdown
$.ajax({
	type: "GET",
	url: "/getModules",
	dataType: "json",
	xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
	success: function(data) {
		console.log(data)
		let dropdown = $("#moduleDropdown");
		dropdown.empty();
		dropdown.append('<option value="">Select Module</option>');

		$.each(data, function() {
			dropdown.append(`<option value="${this.moduleid}">${this.modulename}</option>`);
		});


		dropdown.selectpicker('refresh');
	},
	error: function() {
		console.error("Error fetching module data.");
	}
});

//for Characteristic dropdown
$("#moduleDropdown").change(function() { 
	let moduleId = $(this).val();

	if (moduleId) {
		$.ajax({
			type: "GET",
			url: "/getCharacteristic/" + moduleId,
			dataType: "json",
			xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
			success: function(data) {
				let dropdown = $("#charactersticDrop");
				dropdown.empty();
				dropdown.append('<option value="">Select Characteristic</option>');

				$.each(data, function() {
					dropdown.append(`<option value="${this.characteristicId}">${this.characteristicName}</option>`);
				});

				dropdown.selectpicker("refresh");
			},
			error: function() {
				console.error("Error fetching characteristics.");
			}
		});
	} else {
		$("#charactersticDrop").empty().append('<option value="">Select Characteristic</option>');
	}
});

//for subCharacteristic dropdown
$("#charactersticDrop").change(function() {
	let characteristicId = $(this).val();

	if (characteristicId) {
		$.ajax({
			type: "GET",
			url: "/subcharacteristic/" + characteristicId,
			dataType: "json",
			xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
			success: function(data) {
				console.log(data)
				let dropdown = $("#SubcharactersticDrop");
				dropdown.empty();
				dropdown.append('<option value="">Select SubCharacteristic</option>');

				$.each(data, function() {
					dropdown.append(`<option value="${this.subcharacteristicId}">${this.subcharacteristicName}</option>`);
				});

				dropdown.selectpicker("refresh");
			},
			error: function() {
				console.error("Error fetching subcharacteristics.");
			}
		});
	} else {
		$("#subcharactersticDrop").empty().append('<option value="">Select SubCharacteristic</option>');
	}
});


//for Recurrence dropdown
$.ajax({
	type: "GET",
	url: "/getRecurrence",
	dataType: "json",
	xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
	success: function(data) {
		let dropdown = $("#Recurrence");
		dropdown.empty();
		dropdown.append('<option value="">Select Recurrence</option>');

		$.each(data, function() {
			dropdown.append(`<option value="${this.recurrenceId}">${this.recurrenceName}</option>`)
		});

		dropdown.selectpicker('refresh');

	},

	error: function() {
		console.error("Error Fetching recurrence data");
	}
});

//for month dropdown
$.ajax({
	type: "GET",
	url: "/getmonth",
	dataType: "json",
	xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
	success: function(data) {
		let dropdown = $("#month");
		dropdown.empty();
		dropdown.append('<option value="">Select Month</option>');

		$.each(data, function() {
			dropdown.append(`<option value="${this.monthId}">${this.monthName}</option>`)
		});

		dropdown.selectpicker('refresh');

	},

	error: function() {
		console.error("Error Fetching month data");
	}
});

//for answertype dropdown
$.ajax({
	type: "GET",
	url: "/getanswer",
	dataType: "json",
	xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
	success: function(data) {
		let dropdown = $("#answer");
		dropdown.empty();
		dropdown.append('<option value="">Select the Answer Type</option>');

		$.each(data, function() {
			dropdown.append(`<option value="${this.answerId}">${this.answerName}</option>`)
		});

		dropdown.selectpicker('refresh');

	},

	error: function() {
		console.error("Error Fetching month data");
	}
});

//to genrate the form id
/*$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "/latest-id",
		success: function(response) {
			let formNumber = "FORM-" + response.toString().padStart(2, '0');
			$("#form").val(formNumber);
		},
		error: function(xhr, status, error) {
			console.error("Error fetching latest form ID:", error);
			$("#form").val("FORM-01");
		}
	});
});*/

//to save the form
saveMain.on("click", function(e) {
	e.preventDefault();
	if (!validation()) {
		return;
	}
	//console.log("Sending Questions:", JSON.stringify(questionList));

	let updatedQuestions = [];
	$('#formquestion_datatable tbody tr').each(function() {
		let rowIndex = $(this).index();
		let questionData = questionList[rowIndex];

		if (questionData) {
			updatedQuestions.push(questionData);
		}
	});
	let table = $('#formquestion_datatable').DataTable();
	if (table.rows().count() === 0) {
		showErrorToaster("You must add at least one question before saving the form.");
		return;
	}



	let formData = {
		formTitle: $("#title").val().trim(),
		formAlias: $("#alias").val().trim(),
		module: $("#moduleDropdown").val(),
		characteristic: $("#charactersticDrop").val(),
		subCharacteristic: $("#SubcharactersticDrop").val(),
		recurrence: $("#Recurrence").val(),
		startMonth: $("#month").val(),
		compliancePeriod: $("#period").val(),
		effectiveDate: $("#date_from").val(),
		formActive: $("#active").prop("checked"),
		formText: $("#text").val().trim(),
		questions: questionlist
	};
	//console.log("Form ID:", $("#form").val());


	$.ajax({
		type: "POST",
		url: "/saveForm",
		contentType: "application/json",
		data: JSON.stringify(formData),
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function() {
			console.log("Form Data being sent:", JSON.stringify(formData));
			setTimeout(() => {
				location.reload();
			}, 2000);
			showSuccessToaster("form saved successully")


			$("#portfolio_add_detail").modal("hide")

		},
		error: function(xhr, status, error) {
			console.error("Error in saving form:", error);
			showErrorToaster("Failed to save the form. Please try again.");
		}
	});


})



//display the grid of the form
$(document).ready(function() {
	$.ajax({
		type: "GET",
		url: "/displayForm",
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(data) {
			let tableBody = $("#form_datatable tbody");
			tableBody.empty();

			data.forEach((form) => {
				let formattedFormId = "FORM-" + form.formId.toString();

				let row = `
                   <tr>
                        <td>${formattedFormId}</td>
                        <td>${form.formTitle}</td>
                        <td>${form.formActive ? "Yes" : "No"}</td>
                        <td class="text-center">
                            <a href="javascript:void(0)"   class="text-success fa-size client_add_btn edit-btn" data-form-id="${form.formId}" data-toggle="tooltip" title="Edit">
                                <i class="fa fa-pencil"></i>
                            </a>
                            <span data-toggle="modal" data-target="#all_question_preview">
                                <a href="javascript:void(0)" class="text-info fa-size" data-toggle="tooltip" title="Preview">
                                    <i class="fa fa-eye"></i>
                                </a>
                            </span>
                            <span class="delete-user-alert">
                                <a href="javascript:void(0)" onclick="deleteForm('${form.formId}')" class="text-danger fa-size delete-form" data-id="${form.formId}" data-toggle="tooltip" title="Delete">
                                    <i class="fa fa-trash"></i>
                                </a>
                            </span>
                        </td>
                    </tr>`;
				tableBody.append(row);
			});

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
			$('#form_datatable').DataTable({
				destroy: true,
				scrollX: true,
				"bAutoWidth": true,
				paging: true,
				"bLengthChange": false,
				"columnDefs": [{
					"targets": 3,
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

		},
		error: function(xhr, status, error) {
			console.error("Error fetching forms:", error);
			alert("Failed to load forms. Please try again.");
		}
	});
});

let questionlist = [];
//update the form
function updateForm(formId) {
	console.log("updateForm called with formId:", formId);
	if (!validation()) {
		return;
	}


	let updatedQuestions = questionlist.map(q => ({
		questionId: q.questionId,  
		questionLabel: q.questionLabel || "",
		questionName: q.questionName,
		questionDescription: q.questionDescription || "",
		answerType: q.answerType,
		questionRequired: q.questionRequired,
		choices: Array.isArray(q.choices) ? q.choices : []
	}));



	console.log("Updated Questions before sending:", JSON.stringify(updatedQuestions));


	let formData = {
		formId: formId,
		formTitle: $("#title").val().trim(),
		formAlias: $("#alias").val().trim(),
		module: $("#moduleDropdown").val(),
		characteristic: $("#charactersticDrop").val(),
		subCharacteristic: $("#SubcharactersticDrop").val(),
		recurrence: $("#Recurrence").val(),
		startMonth: $("#month").val(),
		compliancePeriod: $("#period").val(),
		effectiveDate: $("#date_from").val(),
		formActive: $("#active").prop("checked"),
		formText: $("#text").val().trim(),
		questions: updatedQuestions
	};
	console.log("Updated Form Data:", JSON.stringify(formData));
	$.ajax({
		type: "PUT",
		url: `/updateForm/${formId}`,
		contentType: "application/json",
		data: JSON.stringify(formData),
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function() {
			console.log("Updated Form Data:", JSON.stringify(formData));
			setTimeout(() => {
				location.reload();
			}, 2000);
			showSuccessToaster("Form updated successfully!");
			$("#portfolio_add_detail").modal("hide");
		},
		error: function(xhr, status, error) {
			console.error("Error updating form:", error);
			showErrorToaster("Failed to update the form. Please try again.");
		}
	});
}

let table;
$(document).ready(function() {
	if (!$.fn.DataTable.isDataTable('#formquestion_datatable')) {
		table = $('#formquestion_datatable').DataTable({
			paging: true,
			"bLengthChange": false,
			"columnDefs": [{ "targets": 4, "orderable": false }],
			"pageLength": 10,
			language: {
				paginate: {
					next: '<i class="fa fa-angle-double-right">',
					previous: '<i class="fa fa-angle-double-left">'
				}
			},
			dom:
				"<'row'<'col-xl-6 col-lg-6 col-sm-5'pi><'col-xl-5 col-lg-4 col-sm-5'f><'col-xl-1 col-lg-2 col-sm-2 colmspadding text-left'<'toolbar1'>>>" +
				"<'row'<'col-md-12'tr>>",
			fnInitComplete: function() {
				$('div.toolbar1').html(
					'<a href="javascript:void(0)" data-toggle="modal" data-target=".formsorting" class="btn btn-warning btn-padding mb-1 mr-1"><i class="fa fa-sort"></i></a>' +
					'<a href="javascript:void(0)" data-toggle="modal" data-target=".addformquestion" class="btn btn-warning btn-padding mb-1"><i class="fa fa-plus"></i> Add</a>'
				);
			}
		});
	} else {
		table = $('#formquestion_datatable').DataTable();
	}
});

$(document).on("click", ".btn-padding[data-target='.addformquestion']", function() {
    $("#questionLabel").val("");
    $("#questionName").val("");
    $("#description").val("");
    $("#answer").val("").selectpicker('refresh');
    $("#reqans").prop("checked", false);
    $(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();

    $(".addformquestion").modal("show");

    $("#saveQuestion").off().on("click", function(e) {
        e.preventDefault();
        if (!validationQuestion()) return;

        let newQuestion = {
            questionId: null,
            questionLabel: $("#questionLabel").val(),
            questionName: $("#questionName").val(),
            questionDescription: $("#description").val(),
            answerType: $("#answer option:selected").text(),
            questionRequired: $("#reqans").prop("checked"),
            choices: []
        };

        let selectedAnswerType = newQuestion.answerType;
        if (["Single Choice", "Multiple Choice", "Single Select Dropdown", "Multi Select Dropdown"].includes(selectedAnswerType)) {
            let containerClass = selectedAnswerType === "Single Choice" ? ".singlechoicedata" :
                                selectedAnswerType === "Multiple Choice" ? ".multichoicedata" :
                                selectedAnswerType === "Single Select Dropdown" ? ".singleselectdata" :
                                ".multiselectdata";
            let currentContainer = $(".modal.addformquestion.show").find(containerClass).find("table tbody");

            if (currentContainer.find("tr").length === 0) {
                currentContainer.append(`
                    <tr>
                        <td class="text-center border-0" width="5%"><i class="fa fa-arrow-right"></i></td>
                        <td class="border-0 p-1">
                            <div class="form-group mb-0">
                                <input type="text" class="form-control" value="" placeholder="Enter an answer choice in English">
                            </div>
                        </td>
                        <td class="text-center border-0 p-0" width="3%">
                            <a href="javascript:void(0)" class="${selectedAnswerType === 'Multi Select Dropdown' ? 'multiselectadd' : 'singleselectadd'}">
                                <i class="fa fa-plus-square-o font_20 m-t-5 text-default"></i>
                            </a>
                        </td>
                        <td class="text-center border-0 p-0" width="3%">
                            <a href="javascript:void(0)" class="remove-choice">
                                <i class="fa fa-minus-square-o font_20 m-t-5 text-default"></i>
                            </a>
                        </td>
                    </tr>
                `);
            }

            currentContainer.find("tr").each(function(index) {
                let choiceValue = $(this).find("input[type='text']").val().trim();
                console.log(`New Choice ${index + 1}: "${choiceValue}"`);
                newQuestion.choices.push(choiceValue);
            });

            if (newQuestion.choices.some(choice => choice === "") || newQuestion.choices.length < 2 || newQuestion.choices.length > 5) {
                showErrorToaster("All choices must be non-empty and between 2 and 5.");
                return;
            }
        }

        questionlist.push(newQuestion);
        console.log("New question added to questionlist:", newQuestion);

        let index = questionlist.length - 1;
        table.row.add([
            `FS-TRI-OPS-${String(index + 1).padStart(2, "0")}`,
            newQuestion.questionName,
            newQuestion.answerType,
            newQuestion.questionRequired ? "Yes" : "No",
            `<td class="text-center" data-index="${index}">
                <span data-toggle="modal" data-target=".addformquestion">
                    <a href="javascript:void(0)" class="text-success fa-size edit-question-edit">
                        <i class="fa fa-pencil"></i>
                    </a>
                </span>
                <span class="delete-user-alert">
                    <a href="javascript:void(0)" data-index="${index}" class="text-danger fa-size delete-question">
                        <i class="fa fa-trash"></i>
                    </a>
                </span>
            </td>`
        ]).draw();

        $(".addformquestion").modal("hide");
        showSuccessToaster("Question added successfully!");
    });
});

$('#form_datatable').on("click", ".edit-btn", function() {
	let formId = $(this).closest("tr").find(".edit-btn").data("form-id");
	
	$.ajax({
		type: "GET",
		url: `/getFormWithQuestions/${formId}`,
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(formData) {
			$("#form").val(formData.formId);
			$("#title").val(formData.formTitle);
			$("#alias").val(formData.formAlias);
			$("#moduleDropdown").val(formData.module).change();
			setTimeout(() => {
				$("#charactersticDrop").val(formData.characteristic).change();
				setTimeout(() => {
					$("#SubcharactersticDrop").val(formData.subCharacteristic).change();
				}, 500);
			}, 500);
			$("#Recurrence").val(formData.recurrence).change();
			$("#month").val(formData.startMonth).change();
			$("#period").val(formData.compliancePeriod);
			$("#date_from").val(formData.effectiveDate);
			$("#active").prop("checked", formData.formActive);
			$("#text").val(formData.formText);

			table.clear().draw();
			questionlist = formData.questions;

			let rows = questionlist.map((question, index) => [
				`FS-TRI-OPS-${String(index + 1).padStart(2, "0")}`,
				question.questionName,
				question.answerType,
				question.questionRequired ? "Yes" : "No",
				`<td class="text-center" data-index="${index}">
                    <span data-toggle="modal" data-target=".addformquestion">
                        <a href="javascript:void(0)" class="text-success fa-size edit-question-edit">
                            <i class="fa fa-pencil"></i>
                        </a>
                    </span>
                    <span class="delete-user-alert">
                        <a href="javascript:void(0)" class="text-danger fa-size delete-question">
                            <i class="fa fa-trash"></i>
                        </a>
                    </span>
                </td>`
			]);

			table.rows.add(rows).draw();
			$("#savemain").off("click").on("click", function(e) {
				e.preventDefault();
				updateForm(formId);
			});
		},
		error: function() {
			alert("Error loading form data.");
		}
	});
});

$('#formquestion_datatable').on("click", ".edit-question-edit", function() {
    let rowIndex = $(this).closest("tr").index();
    let questionData = questionlist[rowIndex];

    if (!questionData) {
        alert("Question data not found!");
        return;
    }

    $("#questionLabel").val(questionData.questionLabel || "");
    $("#questionName").val(questionData.questionName || "");
    $("#description").val(questionData.questionDescription || "");
    $("#answer").val($("#answer option:contains('" + questionData.answerType + "')").val()).selectpicker('refresh');
    $("#reqans").prop("checked", questionData.questionRequired);

    $(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();

    let choiceContainer;
    if (questionData.answerType === "Single Choice") {
        choiceContainer = $(".singlechoicedata");
    } else if (questionData.answerType === "Multiple Choice") {
        choiceContainer = $(".multichoicedata");
    } else if (questionData.answerType === "Single Select Dropdown") {
        choiceContainer = $(".singleselectdata");
    } else if (questionData.answerType === "Multi Select Dropdown") {
        choiceContainer = $(".multiselectdata");
    }

    if (choiceContainer) {
        choiceContainer.show();
        let choiceTableBody = choiceContainer.find("table tbody");
        choiceTableBody.empty();

        console.log("questionData.choices:", questionData.choices);

        if (Array.isArray(questionData.choices) && questionData.choices.length > 0) {
            questionData.choices.forEach(choice => {
                console.log("Appending choice:", choice);
                choiceTableBody.append(`
                    <tr>
                        <td class="text-center border-0" width="5%"><i class="fa fa-arrow-right"></i></td>
                        <td class="border-0 p-1">
                            <div class="form-group mb-0">
                                <input type="text" class="form-control" value="${choice || ''}" placeholder="Enter an answer choice in English">
                            </div>
                        </td>
                        <td class="text-center border-0 p-0" width="3%">
                            <a href="javascript:void(0)" class="${questionData.answerType === 'Multi Select Dropdown' ? 'multiselectadd' : 'singleselectadd'}">
                                <i class="fa fa-plus-square-o font_20 m-t-5 text-default"></i>
                            </a>
                        </td>
                        <td class="text-center border-0 p-0" width="3%">
                            <a href="javascript:void(0)" class="remove-choice">
                                <i class="fa fa-minus-square-o font_20 m-t-5 text-default"></i>
                            </a>
                        </td>
                    </tr>
                `);
            });
        } else {
            choiceTableBody.append(`
                <tr>
                    <td class="text-center border-0" width="5%"><i class="fa fa-arrow-right"></i></td>
                    <td class="border-0 p-1">
                        <div class="form-group mb-0">
                            <input type="text" class="form-control" value="" placeholder="Enter an answer choice in English">
                        </div>
                    </td>
                    <td class="text-center border-0 p-0" width="3%">
                        <a href="javascript:void(0)" class="${questionData.answerType === 'Multi Select Dropdown' ? 'multiselectadd' : 'singleselectadd'}">
                            <i class="fa fa-plus-square-o font_20 m-t-5 text-default"></i>
                        </a>
                    </td>
                    <td class="text-center border-0 p-0" width="3%">
                        <a href="javascript:void(0)" class="remove-choice">
                            <i class="fa fa-minus-square-o font_20 m-t-5 text-default"></i>
                        </a>
                    </td>
                </tr>
            `);
        }
    }

    $(".addformquestion").modal("show");

    $("#saveQuestion").off().on("click", function(e) {
        e.preventDefault();
        if (!validationQuestion()) return;

        let updatedQuestion = {
            questionId: questionData.questionId || null,
            questionLabel: $("#questionLabel").val(),
            questionName: $("#questionName").val(),
            questionDescription: $("#description").val(),
            answerType: $("#answer option:selected").text(),
            questionRequired: $("#reqans").prop("checked"),
            choices: []
        };

        let selectedAnswerType = updatedQuestion.answerType;
        if (["Single Choice", "Multiple Choice", "Single Select Dropdown", "Multi Select Dropdown"].includes(selectedAnswerType)) {
            let containerClass = selectedAnswerType === "Single Choice" ? ".singlechoicedata" :
                                selectedAnswerType === "Multiple Choice" ? ".multichoicedata" :
                                selectedAnswerType === "Single Select Dropdown" ? ".singleselectdata" :
                                ".multiselectdata";
            let currentContainer = $(".modal.addformquestion.show").find(containerClass).find("table tbody");

            console.log("Collecting from container:", currentContainer);
            console.log("Number of inputs found:", currentContainer.find("input[type='text']").length);

            currentContainer.find("tr").each(function(index) {
                let choiceValue = $(this).find("input[type='text']").val().trim();
                console.log(`Choice ${index + 1}: "${choiceValue}"`);
                updatedQuestion.choices.push(choiceValue);
            });

            console.log("Collected choices:", updatedQuestion.choices);
            console.log("Has empty choice:", updatedQuestion.choices.some(choice => choice === ""));
            console.log("Choices length:", updatedQuestion.choices.length);

            if (updatedQuestion.choices.some(choice => choice === "") || updatedQuestion.choices.length < 2 || updatedQuestion.choices.length > 5) {
                showErrorToaster("All choices must be non-empty and between 2 and 5.");
                return;
            }
        }

        console.log("Updated Question before saving:", updatedQuestion);
        questionlist[rowIndex] = updatedQuestion;

        table.row(rowIndex).data([
            `FS-TRI-OPS-${String(rowIndex + 1).padStart(2, "0")}`,
            updatedQuestion.questionName,
            updatedQuestion.answerType,
            updatedQuestion.questionRequired ? "Yes" : "No",
            `<td class="text-center" data-index="${rowIndex}">
                <span data-toggle="modal" data-target=".addformquestion">
                    <a href="javascript:void(0)" class="text-success fa-size edit-question-edit">
                        <i class="fa fa-pencil"></i>
                    </a>
                </span>
                <span class="delete-user-alert">
                    <a href="javascript:void(0)" data-index="${rowIndex}" class="text-danger fa-size delete-question">
                        <i class="fa fa-trash"></i>
                    </a>
                </span>
            </td>`
        ]).draw();

        $(".addformquestion").modal("hide");
        showSuccessToaster("Question updated successfully!");
    });
});


$('#formquestion_datatable').on("click", ".delete-question", function() {
	let rowIndex = $(this).closest("tr").data("index");
	console.log("row", rowIndex);
	questionlist.splice(rowIndex, 1);


	// Remove row from DataTable properly
	table.row($(this).closest('tr')).remove().draw();

	showSuccessToaster("Question deleted successfully!");
});

function deleteForm(formId) {
	$.ajax({
		url: `/deleteForm/${formId}`,
		type: "DELETE",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function() {
			showSuccessToaster("Form Delete Successfully");
			setTimeout(() => {
				location.reload();
			}, 2000);
		},
		error: function(xhr) {
			console.log("Error in delete ajax:", xhr.responseText);
			showErrorToaster("Error:", xhr.responseText);
		}
	})
}



function validationQuestion() {
	if (questionLabel.val() === "" || questionLabel.val().length < 2) {
		showErrorToaster("Question label is Required and Must be more than 2 letter");
		return false;
	}

	if (questionName.val() === "" || questionName.val().length < 2) {
		showErrorToaster("Question Name is Required and Must be more than 2 letter");
		return false;
	}

	if (answertype.val() === "") {
		showErrorToaster("Please Select the Answer Type");
		return false;
	}
	return true;
}

//to store the question
let questionList = [];
let editingRowIndex = null;
$(document).ready(function() {
	/*let table = $('#formquestion_datatable').DataTable({
		paging: true,
		"bLengthChange": false,
		"columnDefs": [{
			"targets": 4,
			"orderable": false
		}],
		"pageLength": 10,
		language: {
			paginate: {
				next: '<i class="fa fa-angle-double-right">',
				previous: '<i class="fa fa-angle-double-left">'
			}
		},
		dom:
			"<'row'<'col-xl-6 col-lg-6 col-sm-5'pi><'col-xl-5 col-lg-4 col-sm-5'f><'col-xl-1 col-lg-2 col-sm-2 colmspadding text-left'<'toolbar1'>>>" +
			"<'row'<'col-md-12'tr>>",
		fnInitComplete: function() {
			$('div.toolbar1').html('<a href="javascript:void(0)" data-toggle="modal" data-target=".formsorting" class="btn btn-warning btn-padding mb-1 mr-1"><i class="fa fa-sort"></i></a><a href="javascript:void(0)" data-toggle="modal" data-target=".addformquestion" class="btn btn-warning btn-padding mb-1"><i class="fa fa-plus"></i> Add</a>');
		},
	});*/

	$("#answer").on("change", function() {
		let selectedAnswerType = $("#answer option:selected").text();

		$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();

		if (selectedAnswerType === "Single Textbox" || selectedAnswerType === "Multiline Textbox") {
			$(".hidetextvalidation").show();
		} else {
			$(".hidetextvalidation, .showanswershouldbe").hide();
			$("#validatans").prop("checked", false);
		}


		if (selectedAnswerType === "Single Choice") {
			$(".singlechoicedata").show();
		} else if (selectedAnswerType === "Multiple Choice") {
			$(".multichoicedata").show();
		} else if (selectedAnswerType === "Single Select Dropdown") {
			$(".singleselectdata").show();
		} else if (selectedAnswerType === "Multi Select Dropdown") {
			$(".multiselectdata").show();
		}
	});

	$("#validatans").on("change", function() {
		if ($(this).prop("checked")) {
			$(".showanswershouldbe").show();
		} else {
			$(".showanswershouldbe").hide();
			$(".answercombo").val('').selectpicker("refresh");
		}
	});
	//edit populate form

	$('#formquestion_datatable tbody').on("click", ".edit-question-add", function() {
		let row = $(this).closest("tr");
		editingRowIndex = table.row(row).index();
		let questionData = questionList[editingRowIndex];

		// Populate the question fields 
		questionLabel.val(questionData.questionLabel || '');
		questionName.val(questionData.questionName || '');
		description.val(questionData.questionDescription || '');
		answertype.val($("#answer option:contains('" + questionData.answerType + "')").val()).selectpicker('refresh');
		requiredAnswer.prop("checked", questionData.questionRequired);


		$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();
		if (["Single Choice", "Multiple Choice", "Single Select Dropdown", "Multi Select Dropdown"].includes(questionData.answerType)) {
			$(".choicedata").show();

			let choiceContainer;
			if (questionData.answerType === "Single Choice") {
				choiceContainer = $(".singlechoicedata");
			} else if (questionData.answerType === "Multiple Choice") {
				choiceContainer = $(".multichoicedata");
			} else if (questionData.answerType === "Single Select Dropdown") {
				choiceContainer = $(".singleselectdata");
			} else if (questionData.answerType === "Multi Select Dropdown") {
				choiceContainer = $(".multiselectdata");
			}

			choiceContainer.show().find("input[type='text']").val('');

			// Populate choices

			choiceContainer.find(".choice-container").empty();
			questionData.choices.forEach(choice => {
				choiceContainer.find(".choice-container").append(`<input type="text" class="form-control" value="${choice}">`);
			});
		}

		// Show modal for editing
		$(".addformquestion").modal("show");
	});



	saveQuestion.on("click", function(e) {
		e.preventDefault();

		if (!validationQuestion()) {
			return;
		}

		let questionData = {
			questionLabel: questionLabel.val(),
			questionName: questionName.val(),
			questionDescription: description.val(),
			answerType: $("#answer option:selected").text(),
			questionRequired: requiredAnswer.prop("checked"),
			choices: []
		}
		let selectedAnswerType = questionData.answerType;

		if (selectedAnswerType === "Single Choice") {
			$(".singlechoicedata input[type='text']").each(function() {
				let choiceValue = $(this).val().trim();
				if (choiceValue) {
					questionData.choices.push(choiceValue);
				}
			});
		} else if (selectedAnswerType === "Multiple Choice") {
			$(".multichoicedata input[type='text']").each(function() {
				let choiceValue = $(this).val().trim();
				if (choiceValue) {
					questionData.choices.push(choiceValue);
				}
			});
		} else if (selectedAnswerType === "Single Select Dropdown") {
			$(".singleselectdata input[type='text']").each(function() {
				let choiceValue = $(this).val().trim();
				if (choiceValue) {
					questionData.choices.push(choiceValue);
				}
			});
		} else if (selectedAnswerType === "Multi Select Dropdown") {
			$(".multiselectdata input[type='text']").each(function() {
				let choiceValue = $(this).val().trim();
				if (choiceValue) {
					questionData.choices.push(choiceValue);
				}
			});
		} else if (selectedAnswerType === "Single Textbox" || selectedAnswerType === "Multiline Textbox") {
			if ($("#validatans").prop("checked")) {
				let selectedFormat = $(".answercombo option:selected").text();
				questionData.choices.push(selectedFormat);
			}
		}
		if (["Single Choice", "Multiple Choice", "Single Select Dropdown", "Multi Select Dropdown"].includes(selectedAnswerType)) {
			let choiceCount = questionData.choices.length;

			if (choiceCount < 2 || choiceCount > 5) {
				showErrorToaster("Choices must be between 2 and 5.");
				return;
			}
		}
		console.log("Collected Choices:", questionData.choices);


		if ((["Single Choice", "Multiple Choice", "Single Select Dropdown", "Multi Select Dropdown"].includes(selectedAnswerType))
			&& questionData.choices.length === 0) {
			showErrorToaster("Please add at least one choice.");
			return;
		}

		questionList.push(questionData);

		let questionNumbers = `FS-TRI-OPS-${String(questionList.length).padStart(2, "0")}`;
		console.log("Current Question List Length:", questionList.length);

		if (editingRowIndex !== null) {
			
			table.row(editingRowIndex).data([
				//`FS-TRI-OPS-${String(editingRowIndex + 1).padStart(2, "0")}`,
				questionNumbers,
				questionData.questionName,
				questionData.answerType,
				questionData.questionRequired ? "Yes" : "No",
				`<td class="text-center">
                <span data-toggle="modal" data-target=".addformquestion">
                    <a href="javascript:void(0)" class="text-success fa-size edit-question-add">
                        <i class="fa fa-pencil"></i>
                    </a>
                </span>
                <span class="delete-user-alert">
                    <a href="javascript:void(0)" class="text-danger fa-size delete-question">
                        <i class="fa fa-trash"></i>
                    </a>
                </span>
            </td>`
			]).draw(false);
			$(".addformquestion").modal("hide");
			showSuccessToaster("Question updated successfully!");
			editingRowIndex = null; 
			questionLabel.val('');
			questionName.val('');
			description.val('');
			$("#answer").val('').selectpicker('refresh');
			requiredAnswer.prop("checked", false);
			$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").find("input[type='text']").val('');
			$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();


			$(".addformquestion").modal("hide");

		} else {
			
			let questionNumber = `FS-TRI-OPS-${String(questionList.length + 1).padStart(2, "0")}`;
			table.row.add([
				questionNumber,
				questionData.questionName,
				questionData.answerType,
				questionData.questionRequired ? "Yes" : "No",
				`<td class="text-center">
                <span data-toggle="modal" data-target=".addformquestion">
                    <a href="javascript:void(0)" class="text-success fa-size edit-question-add">
                        <i class="fa fa-pencil"></i>
                    </a>
                </span>
                <span class="delete-user-alert">
                    <a href="javascript:void(0)" class="text-danger fa-size delete-question">
                        <i class="fa fa-trash"></i>
                    </a>
                </span>
            </td>`
			]).draw(false);
			showSuccessToaster("Question saved successfully!");
			questionLabel.val('');
			questionName.val('');
			description.val('');
			$("#answer").val('').selectpicker('refresh');
			requiredAnswer.prop("checked", false);
			$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").find("input[type='text']").val('');
			$(".singlechoicedata, .multichoicedata, .singleselectdata, .multiselectdata").hide();


			$(".addformquestion").modal("hide");


		}
		//function delete the question in grid
		$('#formquestion_datatable tbody').on("click", ".delete-question", function() {
			let row = $(this).closest("tr");
			table.row(row).remove().draw(false);
			showSuccessToaster("Question deleted successfully!");
		});


	});
});



$(document).ready(function() {
	
	$(document).on("click", ".text-info.fa-size", function() {
		let formId = $(this).closest("tr").find(".edit-btn").data("form-id"); 
		console.log("Selected Form ID:", formId); 

		if (formId) {
			
			loadFormDetails(formId)
		}
	});
});

function loadFormDetails(formId) {
	$.ajax({
		type: "GET",
		url: `/formDetails/${formId}`,
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(formData) {
			if (!formData) {
				console.error("Form details not found");
				return;
			}

			loadQuestions(formId, formData);
		},
		error: function(xhr, status, error) {
			console.error("Error fetching form details:", error);
		}
	});
}

// Function to fetch and display questions
function loadQuestions(formId, formData) {
	$.ajax({
		type: "GET",
		url: `/questions/${formId}`, 
		dataType: "json",
		xhrFields: {
			withCredentials: true
		},
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		},
		success: function(data) {
			console.log("the question:", data)
			let questionContainer = $("#questionContainer"); 
			questionContainer.empty(); 

			if (data.length === 0) {
				questionContainer.html("<p class='text-muted'>No questions available.</p>");
				return;
			}

			renderQuestions(formData, data, questionContainer);

			
			$("#all_question_preview").modal("show"); 
		},
		error: function(xhr, status, error) {
			console.error("Error fetching questions:", error);
			alert("Failed to load questions. Please try again.");
		}
	});
}

//to formtitle and description
function renderQuestions(formData, questions, container) {
	container.empty();
	
	let formInfoHtml = `
        <div class="detailsbg">
            <div class="row pr-2 pl-2">
                <div class="col-xl-12 col-lg-12 col-sm-12 col-xs-12 colmspadding">
                    <p class="mb-1 font-weight-600">
                        <span class="font-weight-700">Form Title:</span> 
                        <span>${formData.formTitle}</span>
                    </p>
                    <p class="mb-0 font-weight-600">
                        <span class="font-weight-700">Description:</span>
                        <span>${formData.formText}</span>
                    </p>
                </div>
            </div>
        </div>`;

	container.append(formInfoHtml);


	questions.forEach((question, index) => {
		let inputHtml = "";
		console.log("Processing question:", question); 

		
		const { answerType, questionName, questionLabel, questionRequired, questionDescription } = question;
		const choices = question.choices ? question.choices.map(c => c.trim()) : [];

		console.log("Choices for radio:", choices); 

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
                <select class="form-control" 
                data-style="lineheight12 bg-transfer"
                data-live-search="true"
                name="${questionName}">
                    <option value="">Select</option>
                    ${choices.map(c => `<option value="${c}">${c}</option>`).join("")}
                </select>
            `;
		} else if (answerType === "Multi Select Dropdown" && choices.length > 0) {
			inputHtml = `
        <select class="selectpicker" 
        multiple data-selected-text-format="count"
                data-style="btn-light bg-transfer" data-actions-box="true"
                name="${questionName}" 
                multiple data-live-search="true">
            ${choices.map(c => `<option value="${c}">${c}</option>`).join("")}
        </select>
    `;

			setTimeout(() => {
				$('.selectpicker').selectpicker();
			}, 100);
		}
		else if (answerType === "Single Textbox") {
			inputHtml = `<input type="text" class="form-control" name="${questionName}" placeholder="Enter Your Answer">`;
		} else if (answerType === "Multiline Textbox") {
			inputHtml = `<textarea class="form-control textareasize" name="${questionName}" placeholder="Enter Your Answer"></textarea>`;
		} else if (answerType === "Date") {
			inputHtml = `
        <div class="col-xl-3 col-lg-12 col-sm-12 col-xs-12 colmspadding">
            <div class="input-group date">
                <input type="text" class="form-control" placeholder="dd/mm/yyyy" 
                    name="${questionName}" id="${questionName}_date">
                <span class="input-group-addon inputgroups">
                    <i class="mdi mdi-calendar"></i>
                </span>
            </div>
        </div>
    `;
		} else {
			inputHtml = "<p class='text-muted'>No input required.</p>";
		}

		let questionHtml = `
            <div class="card mb-3 queshadow">
                <div class="card-body">
                    <div class="row pl-2 pr-2">
                        <div class="col-xl-1 col-lg-1 col-sm-2 col-xs-12 colmspadding">
                            <span class="question font-weight-bold">Q${index + 1}:</span>
                        </div>
                        <div class="col-xl-11 col-lg-11 col-sm-10 col-xs-12 colmspadding">
                            <div class="form-group mb-0">
                                <p class="font-weight-700 mb-1 text-justify">
                                    ${questionRequired ? '<span class="text-danger">*</span>' : ""}
                                    ${questionName}
                                </p>
                                <p class="mb-1 text-justify">${questionDescription || ""}</p>
                            </div>
                            <div class="form-group">
                                ${inputHtml || "<p class='text-muted'>No options available.</p>"}
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;

		container.append(questionHtml);
	});
}
//moveBack
let moveBack = $("#moveback")
moveBack.on("click", function() {
	$("#form").selectpicker('refresh');
	$("#title").val("")
	$("#alias").val("")
	$("#moduleDropdown").val("").selectpicker('refresh');
	$("#charactersticDrop").val("").selectpicker('refresh');
	$("#SubcharactersticDrop").val("").selectpicker('refresh');
	$("#Recurrence").val("").selectpicker('refresh');
	$("#month").val("").selectpicker('refresh');
	$("#period").val("");
	$("#date_from").val("");
	$("#active").val("");
	$("#text").val("");
	$("#questionLabel").val("")
	$("#questionName").val("")
	$("#description").val("")
	$("#answer").val("")
	$("#reqans").val("")
	$('#formquestion_datatable').DataTable().clear().draw();
});

let cancel = $("#cancel")
cancel.on("click", function() {
	$("#form").selectpicker('refresh');
	$("#title").val("")
	$("#alias").val("")
	$("#moduleDropdown").val("").selectpicker('refresh');
	$("#charactersticDrop").val("").selectpicker('refresh');
	$("#SubcharactersticDrop").val("").selectpicker('refresh');
	$("#Recurrence").val("").selectpicker('refresh');
	$("#month").val("").selectpicker('refresh');
	$("#period").val("");
	$("#date_from").val("");
	$("#active").val("");
	$("#text").val("");
	$("#questionLabel").val("")
	$("#questionName").val("")
	$("#description").val("")
	$("#answer").val("")
	$("#reqans").val("")
	$('#formquestion_datatable').DataTable().clear().draw();
})







