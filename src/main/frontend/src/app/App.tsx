import { useEffect, useState } from "react";

import Header from "components/Header";

function App() {
  const [message, setMessage] = useState("");

  useEffect(() => {
    fetch("/api/message")
      .then((response) => response.text())
      .then((text) => setMessage(text));
  }, []);

  return (
    <div className="App">
      <div>
        <Header />
      </div>
      <h1>{message}</h1>
      <div className="card">
        <p>
          Edit<code>src/App.tsx</code> and save to test HMR
        </p>
        <p className="read-the-docs">
          Click on the Vite and React logos to learn more
        </p>
      </div>
    </div>
  );
}

export default App;
