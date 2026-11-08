// Gabriel J 11/8/26 ~ 1700

// MUI search field and icon
import InputAdornment from '@mui/material/InputAdornment';
import TextField from '@mui/material/TextField';
import SearchIcon from '@mui/icons-material/Search';

import { useMemo, useState } from 'react';

const SEARCH_FILTER = ['studentId', 'name', 'major']; // Exclude Year field

// Holds the search text and returns matching rows
export function useStudentSearch(rows) {
  const [query, setQuery] = useState('');

 // filters the normalized text search against the SEARCH_FILTER fields
  const filteredRows = useMemo(() => {
    const normalized = query.trim().toLowerCase();
    return rows.filter((row) =>
      SEARCH_FILTER.some((field) =>
        String(row[field] ?? '').toLowerCase().includes(normalized)
      )
    );
  }, [rows, query]);

  return { query, setQuery, filteredRows };
}

export default function SearchBox({ value, onChange }) {
  return (

    <TextField
      data-testid="student-search"
      placeholder="Search Table..."
      inputProps={{ 'aria-label': 'Search table' }}
      variant="outlined"
      size="medium"
      value={value}
      onChange={onChange}
      InputProps={{
        startAdornment: (
          <InputAdornment position="start">
            <SearchIcon />
          </InputAdornment>
        ),
      }}
      // in-line styling with sx prop 
      sx={{
        display: 'block',
        mx: 'auto',
        width: 314,
        '& .MuiOutlinedInput-root': { borderRadius: 314 },
      }}
      
    />
   
  );
}