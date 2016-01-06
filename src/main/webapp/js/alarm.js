function accept(alarmIdx){
    var par="alarmIdx="+alarmIdx+"&type=1";
    $("#alarm-"+alarmIdx).remove();
    $.ajax({
        url: "../acceptRequest",
        data: par,
        dataType: 'text',
        async: true,
        processData: false,
        contentType: false,
        type: 'GET',
        success: function () {
            location.reload();
        }
    });
}
function decline(alarmIdx){
    var par="alarmIdx="+alarmIdx+"&type=0";
    $("#alarm-"+alarmIdx).remove();
    $.ajax({
        url: "../acceptRequest",
        data: par,
        dataType: 'text',
        async: true,
        processData: false,
        contentType: false,
        type: 'GET',
        success: function () {
            location.reload();
        }
    });
}
