var dataTable = null;
/**
 * Method  for create jQuery data-table for in-line editable support
 * 
 * @param colsArray : Columns array 
 * @param dataAjaxUrl: Table data fetch URL
 * @param recordPerPage: Display number of records per page
 * @param printColumns: Provides number of columns visible for print
 * @param dataTableDiv: selector of data-table table tag
 * @returns
 */
var namespace = $('#namespace').text();
var colsArray = [];
colsArray.push(
		{
			data: 'name',
			title:'Name'
		},
		{
			data: 'description', 
			title: 'Description'
		},
		{
			data: 'length', 
			title: 'Length',
		},
		{
			data: 'height', 
			title: 'Height',
		},
		{
			data: 'area', 
			title: 'Area',
		},
		{
			data: 'nr', 
			title: 'Nr',
		});

//Create Datatable for display of the data
$("#"+namespace+"goods-list-table").DataTable({
	ajax : $("#getGoodsListURL").text(),
	columns : colsArray,
});
