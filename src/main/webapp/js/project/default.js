/**
 * Created by seongahjo on 2016. 7. 23..
 */
'use strict'
function init() {
    $("a.zoom").imageZoom({scale: 0.75});

//Initialize Select Elements
    $(".select2").select();
//Date range picker
    var reservation = $('#reservation');
    reservation.daterangepicker();
    reservation.on('apply.daterangepicker', function (ev, picker) {
        scheduleStart = picker.startDate.format('YYYY-MM-DD');
        scheduleEnd = picker.endDate.format('YYYY-MM-DD');
    });

    var scheduleReservation = $('#scheduleReservation');
    scheduleReservation.daterangepicker();
    scheduleReservation.on('apply.daterangepicker', function (ev, picker) {
        scheduleStart = picker.startDate.format('YYYY-MM-DD');
        scheduleEnd = picker.endDate.format('YYYY-MM-DD');
    });

    Dropzone.options.dropzone = {
        clickable: false,
        maxThumbnailFilesize: 5,
        dictDefaultMessage: '',
        init: function () {

            this.on('success', function (file, json) {
                socket.emit("file", {
                    msg: json,
                    user: user.name,
                    date: new Date().toString('HH:mm'),
                    type: json.type
                });
            });

            this.on('addedfile', function (file) {

            });

            this.on('drop', function (file) {

            });
        }
    };

    table = $('#example2').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": false,
        "autoWidth": false,
        "ajax": {
            'url': '../file/' + projectIdx
        }
    });

    $('#InviteUser').on('hidden.bs.modal', function (e) {
        $("#user").html('');
        $("#inviteForm #inviteId").val('');
    });
    $('#todoMadal').on('hidden.bs.modal', function (e) {
        $("#todocontent").val('');
        $("#reservation").val('');
    });

    function selectFile() {
        document.getElementById("file").click();
    }

    $("#selectBox").change(function () {
        option = $(this).children("option:selected").text();
        if (option == "Today")
            $("#memo").val(currentMinute);
        else
            $("#memo").val($(this).children("option:selected").val());
    });

    $("#inviteId").keydown(function (key) {
        if (key.keyCode == 13) {
            search();
        }
    });

    $('#file').hover(function (event) {
        $('#file_over').addClass('front_hover');
    }, function () {
        $('#file_over').removeClass('front_hover');
    });
}