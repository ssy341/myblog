$(document).ready(function () {
    $.ajax({
        url: "/src/nav/fetchLinks.php",
        dataType: "json",
        success: function (data) {
            $.each(data, function (i, obj1) {
                $(".hero-unit").append("<p class='" + obj1.id + "'>" + obj1.name + ":</p>");
                $.each(obj1.links, function (j, obj2) {
                    $(".hero-unit p." + obj1.id).append("<a class='btn btn-link' href='" + obj2.href + "' title='" + obj2.title + "' target='" + obj2.target + "'>" + obj2.title + "</a>");
                });
            });
        }
    });
});