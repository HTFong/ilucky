
import React, {useState} from "react";
import PopupBGDeposit from "../images/svgPopup/PopupLogin.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi, mainUrl } from "../util/api/requestUtils";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupDeposit = ({ onClose, onSuccess}) => {
  const { t } = useTranslation();
  const [disabled, setDisabled] = useState(false);
  const [bankCode,setBankCode] = useState("")
  const [amount,setAmount] = useState("")
  const [paymentUrl, setPaymentUrl] = useState("")

  const onSubmit = async () => {
    // onClose();
    setDisabled(true);
    try {
      const params = '?'+ new URLSearchParams({bankCode,amount}).toString();  
      const url = mainUrl + "/api/deposit/pay" + params;
      const res = await callApi(url, "GET", {});
      console.log("res", res);
      if (res) {
        setPaymentUrl(res.data)
        onSuccess(res);
      }
    } catch (error) {
      setDisabled(false)
      console.log("error", error);
    }
  };

  return (
    <>
      <div className="main-history ct-flex-col">
        <div
          style={{
            position: "relative",
            width: 330,
            height: gui.screenHeight,
            marginTop: 80,
          }}
        >
          <div
            onClick={onClose}
            style={{
              position: "absolute",
              zIndex: 10000,
              top: 10,
              right: -16,
              cursor: "pointer",
            }}
          >
            <img className="" src={IconX} />
          </div>
          <div
            className="title-popup"
            style={{
              position: "absolute",
              left: 120,
              top: 7,
              color: "#4C2626",
              fontSize: 14,
              width: "100px",
              textAlign: "center",
            }}
          >
            {t("Login")}
          </div>
          <div
            style={{
              position: "absolute",
              height: 190,
              width: "88%",
              top: 26,
              left: 4,
              padding: "30px 16px 16px 16px",
              justifyContent: "center",
            }}
            className="ct-flex-col"
          >
            <div>{t("BankCode")}</div>
            <div
              style={{
                width: 300,
                height: 33,
                border: "1px solid #B46C6C",
                backgroundColor: "#FFF",
                borderRadius: 50,
                justifyContent: "center",
                color: "#000",
              }}
              className="ct-flex-row"
            >
              <select
                value={bankCode}
                onChange={(e) => setBankCode(e.target.value)}
                style={{
                  outline: "none",
                  border: "none",
                  background: "transparent",
                  textAlign: "center",
                }}
              >
                <option disabled selected value=""> -- select an option -- </option>
                <option value="NCB">NH Quoc Dan NCB</option>
                <option value="VCB">NH Ngoai Thuong VCB</option>
                <option value="BIDV">NH Dau Tu va Phat Trien BIDV</option>
                <option value="TCB">NH Ky Thuong TCB</option>
              </select>
            </div>

            <div>{t("Amount")}</div>
            <div
              style={{
                width: 300,
                height: 33,
                border: "1px solid #B46C6C",
                backgroundColor: "#FFF",
                borderRadius: 50,
                justifyContent: "center",
                color: "#000",
              }}
              className="ct-flex-row"
            >
              <input
                type="number"
                min="0"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                style={{
                  outline: "none",
                  border: "none",
                  background: "transparent",
                  textAlign: "center",
                }}
              />
            </div>

            <div className="ct-flex-row">
            <button
              style={{ marginTop: 8 }}
              onClick={onSubmit}
              className="button-ok"
              disabled={disabled}
            >
              {t("Confirm")}
            </button>
            <button
              style={{ marginTop: 8 }}
              onClick={(e) => window.open(paymentUrl,'_blank')}
              className="button-ok"
              disabled={!disabled}
            >
              {t("Payment")}
            </button>
            </div>
            
          </div>
          <img className="" style={{}} src={PopupBGDeposit} />
        </div>
      </div>
    </>
  );
};

export default PopupDeposit;
