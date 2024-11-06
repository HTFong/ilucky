
import React, {useState} from "react";
import PopupBGRegister from "../images/svgPopup/PopupLogin.svg";
import IconX from "../images/svgPopup/IconX.svg";

import "./styles.css";
import { callApi, mainUrl } from "../util/api/requestUtils";
import { useTranslation } from "react-i18next";
import gui from "../util/gui";

const PopupRegister = ({ onClose, onSuccess}) => {
  const { t } = useTranslation();
  const [disabled, setDisabled] = useState(false);
  const [username,setUsername] = useState("")
  const [password,setPassword] = useState("")

  const onSubmit = async () => {
    // onClose();
    setDisabled(true);
    try {
      const url = mainUrl + "/api/auth/register";
      const res = await callApi(url, "POST", {username,password});
      if (res.status ==="201") {
        onSuccess(res);
      }
    } catch (error) {
      console.log("register-error", JSON.stringify(error));
      onSuccess();
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
          <div className="title-popup"
            style={{
              position: "absolute",
              left: 120,
              top: 7,
              color: "#4C2626",
              fontSize: 14,
              width: "100px",
              textAlign: "center"
            }}
          >
            {t("Register")}
          </div>
          <div
            style={{
              position: "absolute",
              height: 190,
              width: "88%",
              top: 26,
              left: 4,
              padding: "30px 16px 16px 16px" ,
              justifyContent: "center",
            }}
            className="ct-flex-col"
          >
            <div>{t("Username")}</div>
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
              <input type="text" value={username}
                onChange={(e) => setUsername(e.target.value)} 
                style={{ outline: "none",border: "none",background: "transparent",textAlign: "center"}}/>
            </div>
            
            <div>{t("Password")}</div>
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
              <input type="password" value={password}
                onKeyDown={(e) => {if (e.key==='Enter') onSubmit() }}
                onChange={(e) => setPassword(e.target.value)} 
                style={{ outline: "none",border: "none",background: "transparent",textAlign: "center"}}/>
            </div>
            
            <button
              style={{ marginTop: 8 }}
              onClick={onSubmit}
              className="button-ok"
              disabled={disabled}
            >
              {t("Register")}
            </button>
          </div>
          <img className="" style={{}} src={PopupBGRegister} />
        </div>
      </div>
    </>
  );
};

export default PopupRegister;
