import { useState, useEffect } from "react";

function App() {
  const [loading, setLoading] = useState(true);
  const [coins, setCoins] = useState([]);

  const [selectedValue, setSelectedValue] = useState(0);
  const [cash,setCash] = useState(0); // 입력 금액
  const [calculateCoin, setCalculateCoin] = useState(0); // 계산된 금액

  const onChangeCash = (event) => setCash(event.target.value); // 입력된 금액 

  const onChangeSelectValue = (event) => { // 옵션에서 선택 된 값
    event.preventDefault();
    console.log("setSelectedValue", setSelectedValue);
    setSelectedValue(event.target.value);
  };

  
  const onSubmitCash = (event) => {
    event.preventDefault();
    if (cash === "") {
      return;
    }
    console.log("cash",cash);
    console.log("selectedValue",selectedValue);

    setCalculateCoin( selectedValue / cash);
  }
  console.log("setCalculateCoin1->", calculateCoin);
    
  const reset = () => {
    setCash(0);
    setSelectedValue(0);
    setCalculateCoin(0);
    console.log("setCalculateCoin2->", calculateCoin);
  }

  useEffect(() => {
    fetch("https://api.coinpaprika.com/v1/tickers")
      .then((response) => response.json())
      .then((json) => {
        setCoins(json);
        setLoading(false);
      });
  }, []);
  
  return (
    <div>
      <h1>The Coins! ({coins.length})</h1>
      {loading ? <strong>Loading...</strong> : (
        <select value={selectedValue} onChange={onChangeSelectValue}>
          <option value={0}>Select a coin</option>
          {coins.map((coin) => (
            <option key={coin.id} value={coin.quotes.USD.price}>
              {coin.name} ({coin.symbol}) : ${coin.quotes.USD.price} USD
            </option>
          ))}
        </select>
      )}
      <br /><br />
      <div>
      <form onSubmit={onSubmitCash}>
        <input value={cash} onChange={onChangeCash} type="number" placeholder="금액 입력"/>
        <button>Calculate</button>
      </form>
        <button onClick={reset}>Reset</button>
      <h2>{calculateCoin === 0 ? "" : <strong>You can get {calculateCoin}</strong>} </h2>
      </div>

    </div>
  );
}

export default App;
