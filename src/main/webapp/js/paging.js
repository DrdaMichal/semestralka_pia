$(document).ready(function () {
    $('#transactions').DataTable( {

        searching: false,
        ordering:  false,
        select: false,
        paging: true,
        //pagingType: "simple",
        ext: {
            classes: {
                sPageButton: 'btn btn-default'
            }
        },
        language: {
            paginate: {
                next: "Next",
                previous: "Previous"
            }
        }
    });
});