// console.log("ready!");
// let isEditing = false; // Глобальная переменная для отслеживания редактирования
//

function delete_task(task_id){
    let url = getBaseURL() + task_id;
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
           window.location.reload()
        }
    });
}
function edit_task(task_id) {
    let identifier_delete = "#delete_" + task_id;
    $(identifier_delete).remove();
    let identifier_edit = "#edit_" + task_id;
    let save_tag = "<button id='save_" + task_id + "'>Save</button>";
    $(identifier_edit).html(save_tag);
    let property_save_tag= "update_task("+ task_id +")";
    $(identifier_edit).attr("onclick",property_save_tag);

    let current_tr_element = $(identifier_edit).parent().parent();
    let children = current_tr_element.children();
    let td_description = children.eq(1);
    td_description.html("<input id='input_description_" + task_id + "' type='text' value='" + td_description.text() + "'>");

    let td_status = children.eq(2);
    let status_id = "#select_status" + task_id;
    let status_current_value = td_status.text();
    td_status.html(getDropdownStatusHTML(task_id));
    $(status_id).val(status_current_value).change();
}

function getDropdownStatusHTML(task_id) {
    let status_id = "select_status" + task_id;
    return "<label for='status'></label>"
        + "<select id=" + status_id + " name='status'>"
        + "<option value='IN_PROGRESS'>IN_PROGRESS</option>"
        + "<option value='DONE'>DONE</option>"
        + "<option value='PAUSED'>PAUSED</option>"
        + "</select>";
}
function update_task(task_id){
    let url = getBaseURL() + task_id;

    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#select_status" + task_id).val();

    $.ajax({
        url: url,
        type: 'PATCH',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });
    setTimeout(()=> {
        document.location.reload();
    }, 300)
}
function add_task(task_id){
    let url = getBaseURL();

    let value_description = $("#description_new").val();
    let value_status = $("#status_new").val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });
    setTimeout(()=> {
        document.location.reload();
    }, 300)
}
function getBaseURL(){
    let current_pass = window.location.href;
    let end_position = current_pass.indexOf('?');
    return current_pass.substring(0, end_position);
}