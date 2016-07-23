'use strict'
var socket;
function socket_init(user,projectIdx){
    socket = io.connect('127.0.0.1:9999');
    socket.emit('join', {
        projectIdx: projectIdx.toString(),
        userIdx:user.useridx,
        userName: user.name,
        userId: user.id,
        userImg: user.img
    });

    socket.on('finish', function () {
        $("#writebutton").removeClass("hidden");
        $("#savebutton").addClass("hidden");
        $("#memo").prop("disabled", true);
        $("#writer").text("?");
    })

    socket.on('response', function (data) {
        if (data.type == 'img' || data.type == 'file')
            table.ajax.reload();
        if (data.user == "${user.id}") {
            $("#chat").append('<div class="direct-chat-msg right"> <div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-right">' + data.user + '</span> </div> <img class="direct-chat-img" src=../' + data.img + ' alt="message user image"> <div class="direct-chat-text pull-right"> ' + data.msg + '</div> </div> <span class="direct-chat-timestamp pull-right" >' + data.date + '</span><br>');
        }
        else
            $("#chat").append('<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-left">' + data.user + '</span> </div> <img class="direct-chat-img" src=../' + data.img + ' alt="message user image"> <div class="direct-chat-text pull-left"> ' + data.msg + '</div> </div>  <span class="direct-chat-timestamp pull-left ts-left" >' + data.date + '</span><br>');

        $('#chat').scrollTop($('#chat')[0].scrollHeight);
    });
    socket.on('write', function (response) {
        response = JSON.parse(response);
        if (response.flag == "yes") {
            $("#memo").prop("disabled", false);
            $("#writebutton").addClass("hidden");
            $("#savebutton").removeClass("hidden");
        }
        else {
            $("#memo").prop("disabled", true);
        }
        $("#writer").text(response.writer);
    });
    socket.on('adduser', function (id) {
        $("#user" + id + "off").addClass("hidden");
        $("#user" + id + "on").removeClass("hidden");
        $("#userlist-" + id).find($("#userstatus")).text(id + " is now ONLINE");
    });
    socket.on('deleteuser', function (id) {
        $("#user" + id + "off").removeClass("hidden");
        $("#user" + id + "on").addClass("hidden");
        $("#userlist-" + id).find($("#userstatus")).text(id + " is now OFFLINE");
    });
    socket.on('refresh', function (memo) {
        $("#memo").val(memo);
        Tminute = memo;
    });
    socket.on('alarm', function (data) {
        var par = "userIdx="+user.useridx;
        $.ajax({
            url: "../updateAlarm",
            data: par,
            dataType: 'json',
            type: 'GET',
            success: function (data) {
                var size = parseInt($("#alarm-size").text()) + 1;
                $("#alarm").effect("bounce", {direction: 'left', distance: 13, times: 3}, 500);
                $("#alarm-size").text(size);
                $("#alarm-content").text('You have ' + size + 'notifications');
                $("#alarm-list").prepend('<li id="alarm-"' + data.alarmidx + '><a href="#">' +
                    '<i class="fa fa-users text-aqua"></i><strong>' + data.actorid + '</strong>' +
                    'has invited you to <strong>' + data.projectname + '</strong>' +
                    '<div style="float:right;">' +
                    ' <button type="button" class="btn btn-primary btn-xs"' +
                    'onclick=accept("' + data.alarmidx + '")>Ok</button>' +
                    '<button type="button" class="btn btn-default btn-xs"' +
                    'onclick=decline("' + data.alarmidx + '")>Cancel' +
                    '</button>' +
                    '</div>' +
                    '</a>' +
                    '</li>');
            }
        });
    });
}


function save_memo() {
    socket.emit('save', {memo: $("#memo").val()});
    Tminute = $("#memo").val();
    $("#selectBox").attr("disabled", false);
}

function write_memo() {
    if (option == "Today") {
        socket.emit('writer');
        $("#selectBox").attr("disabled", true);
    }
}

$('#memo').keyup(function (event) {
    if (event.keyCode != 8)
        socket.emit('refreshToAll', {memo: $("#memo").val()});
});

function sendMsg() {
    var dates = new Date().toShortTimeString();
    socket.emit('msg', {msg: $("#typing").val(), date: new Date().toString('HH:mm')});
    $("#typing").val("");
    $('#chat').scrollTop($('#chat')[0].scrollHeight);
}