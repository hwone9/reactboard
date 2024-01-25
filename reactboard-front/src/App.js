import {Route, Routes} from "react-router-dom";
import Home from './views/Home';
import BoardList from './views/boards/BoardList';
import BoardDetail from './views/boards/BoardDetail';
import BoardWrite from './views/boards/BoardWrite';
import BoardUpdate from './views/boards/BoardUpdate';

function App() {
  return (
      <Routes>
          <Route path="/" element={<Home/>}/>
          <Route path="/board" element={<BoardList/>}/>
          <Route path="/board/:idx" element={<BoardDetail/>}/>
          <Route path="/write" element={<BoardWrite/>}/>
          <Route path="/update/:idx" element={<BoardUpdate/>}/>
      </Routes>
  );
}

export default App;
