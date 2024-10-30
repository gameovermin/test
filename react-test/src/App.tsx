import React, { useState } from 'react';
import './App.css';
import axios from 'axios';

const DOMAIN = 'http://localhost:8080';
const BOOK_API = 'api/test/books';

interface getBookCategoryResponseDto {
  book_title: string;
  book_author: string;
  book_category: string;
}

function App() {
  const [results, setResults] = useState<any[]>([]);

  const handleBooksChange = () => {
    fetchBookData();
  }

  const fetchBookData = async () => {
    try{
      const res = await axios.get(
        `${DOMAIN}/${BOOK_API}`
      );
      console.log(res);
      const data = res.data.data;

      setResults(data);
    } catch (error) {
      console.log("Error fetching data : ", error);
    }
  }

  return (
    <div>
      <label htmlFor="">Book 전체 조회</label>
      <br />
      <button onClick={handleBooksChange}>전체 조회</button>
      <ul>
        {results.map((result, index) => (
          <li key={index}>{result.book_title}  |  {result.book_author}  |  {result.book_category}</li>
        ))}
      </ul>
    </div>
  )
}

export default App;
