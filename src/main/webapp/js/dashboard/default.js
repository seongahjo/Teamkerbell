/**
 * Created by seongahjo on 2016. 9. 14..
 */

function init() {
    $(".cb").change(function () {
        var check = $(this);
        var par = "id=" + $(this).val();
        $.ajax({
            url: "../todocheck",
            data: par,
            dataType: 'text',
            async: true,
            type: 'GET',
            success: function () {
                check.parent().toggleClass("done");

            }
        });
    });
}