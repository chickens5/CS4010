// Gabriel J 11/8/26 ~ 1700

import DeleteIcon from '@mui/icons-material/Delete';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import { DataGrid, GridToolbar } from '@mui/x-data-grid';

import { useEffect, useState } from "react";
import axios from "axios";

import { API_URL } from "../api/studentAPI";

import AddStudent from "./AddStudent";
import EditStudent from "./EditStudent";
import SearchBox, { useStudentSearch } from "./SearchBox";
import "./StudentTable.css";

function StudentTable() {
  const [students, setStudents] = useState([]);
  const search = useStudentSearch(students);

  const fetchStudents = async () => {
    const response = await axios.get(`${API_URL}`); // returns Promise

    console.log(response.data);
    setStudents(response.data);
  };

  const deleteStudent = async (studentId) => {
    await axios.delete(`${API_URL}/${studentId}`);
    fetchStudents();
  };

  useEffect(() => {
    fetchStudents();
  }, []);

  const handleClickDelete = (studentId) => {
    deleteStudent(studentId);
  }


  const columns = [
    { field: 'studentId', headerName: 'Student ID', width: 120, headerAlign: 'center', align: 'center', headerClassName: 'custom-header' },
    { field: 'name', headerName: 'Name', width: 180, headerAlign: 'center', align: 'center', headerClassName: 'custom-header' },
    { field: 'major', headerName: 'Major', width: 180, headerAlign: 'center', align: 'center', headerClassName: 'custom-header' },
    { field: 'year', headerName: 'Year', width: 80, headerAlign: 'center', align: 'center', headerClassName: 'custom-header' },
    {
      field: 'edit',
      headerName: '',
      width: 80,
      sortable: false,
      filterable: false,
      disableColumnMenu: true,
      headerClassName: 'custom-header',
      headerAlign: 'center',
      align: 'center',
      renderCell: (params) =>
        <EditStudent studentData={params.row} setStudents={setStudents} />
    },
    {
      field: 'delete',
      headerName: '',
      width: 80,
      sortable: false,
      filterable: false,
      disableColumnMenu: true,
      headerClassName: 'custom-header',
      headerAlign: 'center',
      align: 'center',
      renderCell: (params) => (
        <Tooltip data-testid={`delete-button-row-${params.row.studentId}`} title="Delete Student">
          <IconButton aria-label="delete" size="small" onClick={() => handleClickDelete(params.row.studentId)}>
            <DeleteIcon fontSize="small" />
          </IconButton>
        </Tooltip>
      ),
    },
  ];

  return (
    <div className="bg-container">
      <div className="tbl-container">

        {/* Filters on every keystroke */}
        <SearchBox value={search.query} onChange={(e) => search.setQuery(e.target.value)} />

        <div className="btn-container">
          <AddStudent setStudents={setStudents}/>
        </div>

        <DataGrid data-testid="student-table"
          rows={search.filteredRows}
          columns={columns}
          disableRowSelectionOnClick={true}
          getRowId={row => row.studentId}
          getRowClassName={(params) =>
            params.indexRelativeToCurrentPage % 2 === 0 ? 'hot' : 'cold'
          }

    // Extra Feature:
    // built-in toolbar quick filter that debounces searches instead of by keystroke
          slots={{ toolbar: GridToolbar }}
                slotProps={{
                toolbar: {
                    showQuickFilter: true,
                    quickFilterProps: { debounceMs: 500 }, // delays the search execution 
                },
                }}
          sx={{
            boxShadow: 2,
            '& .MuiDataGrid-cell:hover': {
              color: 'primary.main',
            },
            '& .cold': {
              backgroundColor: '#e7f0f7',
              color: 'black',
            },
            '& .hot': {
              backgroundColor: '#ccdfef',
              color: 'black',
            },
          }}

        />
        
      </div>
    </div>
  );
}

export default StudentTable;