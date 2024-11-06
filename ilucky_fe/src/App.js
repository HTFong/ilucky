import "./App.css";

import "./css/typo/typo.css";
import "./css/hc-canvas-luckwheel.css";
import hcLuckywheel from "./js/hc-canvas-luckwheel";
import { useEffect, useState, useRef } from "react";
import gui from "./util/gui";
import Background from "./images/free/bg1.png";
import Vongquay from "./images/free/Vongquay.png";
import HieuUngSao from "./images/free/HieuUngSao.png";
import IconKimQuay from "./images/icon/IconKimQuay.svg";
import IconEN from "./images/icon/IconEN.svg";
import IconVI from "./images/icon/IconVI.svg";
import IconLoa from "./images/icon/IconLoa.svg";
import IconLoaTat from "./images/icon/IconLoaTat.svg";
import bangdon from "./images/icon/bangdon.svg";
import Congratulation from "./images/icon/Congratulation.svg";
import LuckyRoulette from "./images/icon/LuckyRoulette.svg";

import IconVuongMieng from "./images/svg/IconVuongMieng.svg";
import IconTui from "./images/svg/IconTui.svg";
import IconSach from "./images/svg/IconSach.svg";
import logout from "./images/svg/logout.svg";
import IconFree from "./images/svg/IconFree.svg";
import IconLogin from "./images/svg/IconLogin.svg";
import IconRegister from "./images/svg/IconRegister.svg";
import IconDeposit from "./images/svg/IconDeposit.svg";
import { callApi, mainUrl } from "./util/api/requestUtils";
import React from "react";
import Icon1 from "./images/svgquatang/icon1.svg";
import IconSamsung from "./images/quatang/SS.svg";
import Gift from "./images/quatang/GIFT.svg";
import IconLetter from "./images/quatang/LETTER.svg";
import IconStars from "./images/quatang/STARS.svg";
import IconFBU from "./images/quatang/FBU.svg";
import mp3Main from "./mp3/lucky_spin.mp3";
import mp3Done from "./mp3/lucky_done.mp3";
import PopupQua from "./components/PopupQua";
import PopupHistory from "./components/PopupHistory";
import PopupTopStar from "./components/PopupTopStar";
import ViewText from "./components/ViewText";
import PopupHuongDan from "./components/PopupHuongDan";
import PopupBuyTurn from "./components/PopupBuyTurn";
import { useTranslation } from "react-i18next";
import "./util/i18n";
import PopupLogin from "./components/PopupLogin";
import PopupRegister from "./components/PopupRegister";
import PopupDeposit from "./components/PopupDeposit";
import PopupGetFreeTurn from "./components/PopupGetFreeTurn";

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
export const prizes = [
  {
    text: "Share",
    img: Icon1,
    number: 1, // 1%,
    giftCode: ["SHARE"],
    lucky: 0,
    label: "Share",
  },
  {
    text: "VND",
    img: IconFBU,
    number: 1,
    giftCode: ["200VND", "500VND", "1000VND", "10000VND"],
    lucky: 1,
    label: "10000 VND",
  },
  {
    text: "GoodLuck",
    img: Gift,
    number: 1,
    giftCode: ["UNLUCKY"],
    lucky: 2,
    label: "Chúc bạn may mắn lần sau",
  },
  {
    text: "Letter",
    img: IconLetter,
    number: 1,
    giftCode: ["L", "U", "M", "I", "T", "E", "L1"],
    lucky: 3,
    label: "Letter",
  },
  {
    text: "Galaxy S23",
    img: IconSamsung,
    number: 1, // 1%,
    giftCode: ["SAMSUNG1", "SAMSUNG2", "SAMSUNG3", "SAMSUNG4"],
    lucky: 4,
  },
  {
    // text: "Stars",
    text: "Stars",
    img: IconStars,
    giftCode: ["5STARS", "55STARS", "555STARS", "5555STARS"],
    lucky: 5,
    label: "5555 Stars",
  },
];

const convertStringToArray = (str) => {
  let converPath = str
    .split(/[&]+/)
    .map((i) => i.trim())
    .map((i) => ({
      type: i.split(/[=]+/)[0],
      value: i.split(/[=]+/)[1],
    }));
  return converPath;
};

const App = () => {
  const paramsArray = window.location.search
    ? convertStringToArray(
        window.location.search.slice(1, window.location.search.length)
      )
    : [];

  const languageUrl = paramsArray.find((o) => o.type === "lang")?.value || "VI";
  const tokenLocal = localStorage.getItem("token") || "";

  const [tokenState, setTokenState] = useState(tokenLocal);

  const [isLogin, setIsLogin] = useState(false);
  const [isEN, setIsEN] = useState(false);
  const [isMute, setIsMute] = useState(false);
  const [showQua, setShowQua] = useState(false);
  const [ItemTrungThuong, setItemTrungThuong] = useState("");
  const [showHistory, setShowHistory] = useState(false);
  const [showTopStar, setShowTopStar] = useState(false);
  const [showHuongDan, setShowHuongDan] = useState(false);
  const [showDangNhap, setShowDangNhap] = useState(false);
  const [showDangKi, setshowDangKi] = useState(false);
  const [showNaptien, setShowNaptien] = useState(false);
  const [showGetFreeTurn, setShowGetFreeTurn] = useState(false);
  const [countPlayTurn, setCountPlayTurn] = useState(0);

  const [buyMore, setBuyMore] = useState(false);
  const [messageError, setMessageError] = useState("");

  const { t, i18n } = useTranslation();

  const audioRef = useRef(null);
  const audioDoneRef = useRef(null);

  const timeout = (ms) => {
    return new Promise((resolve) => setTimeout(resolve, ms));
  };

  useEffect(() => {
    if (messageError) {
      toast(t(messageError));
      setMessageError("");
    }
  });

  useEffect(() => {
    if(tokenLocal) {
      let isCancelled = false;
      const handleChange = async () => {
        await timeout(5000);
        if (!isCancelled) {
          setTokenState(tokenLocal);
          wsGetLuckyPlayTurn();
        }
      };
      handleChange();
      return () => {
        isCancelled = true;
      };
    }
  });

  // useEffect(() => {
  //   if (tokenLocal) {
  //     setTokenState(tokenLocal);
  //     wsGetLuckyPlayTurn();
  //   }
  // });

  useEffect(() => {
    if (!audioRef.current) {
      audioRef.current = new Audio(mp3Main);
    }
    if (!audioDoneRef.current) {
      audioDoneRef.current = new Audio(mp3Done);
    }
    audioRef.current.muted = isMute;
    audioDoneRef.current.muted = isMute;
  }, [isMute]);

  useEffect(() => {
    i18n.changeLanguage(isEN ? "EN" : "VI");
  }, [isEN]);

  useEffect(() => {
    i18n.changeLanguage(languageUrl || "VI");
  }, [languageUrl]);

  useEffect(() => {
    fetchData();
  }, []);

  // Lấy số lượt chơi đang có
  const wsGetLuckyPlayTurn = async () => {
    try {
      const url = mainUrl + "/api/user/info";
      const res = await callApi(url, "POST", {});
      const { totalPlay } = res.status === "200" ? res.data : null;
      if (res.status === "200") {
        setCountPlayTurn(totalPlay || 0);
        setIsLogin(true);
      } else {
        setMessageError(res.message);
      }
    } catch (error) {
      console.log("error", error);
    }
  };

  const fetchData = () => {
    hcLuckywheel.init({
      id: "luckywheel",
      config: function (callback) {
        callback && callback(prizes);
      },
      mode: "both",
      getPrize: async function (callback) {
        audioRef.current.play();
        try {
          const url = mainUrl + "/api/lucky/play";
          const res = await callApi(url, "POST", {});
          const { gift } = res;
          const found =
            gift.id &&
            prizes.find((o) => o.giftCode.find((k) => k === gift.id));
          setItemTrungThuong({
            ...res,
            ...found,
            code: gift.id,
          });
          if (found) {
            callback && callback([found?.lucky || 0, found?.lucky || 0]);
          } else if (res?.totalPlay === 0) {
            alert("Hết lượt quay");
          }
        } catch (error) {
          console.log("quay-error", error);
          setMessageError("OutOfTurn")
        }
      },
      gotBack: function (data) {
        audioRef.current.pause();
        audioDoneRef.current.play();
        audioRef.current.currentTime = 0;
        setShowQua(true);
        wsGetLuckyPlayTurn();
      },
    });
  };

  const callbackOk = async () => {
    setShowQua(false);
    audioDoneRef.current.pause();
    audioRef.current.currentTime = 0;
  };

  const onLogout = (v) => {
    localStorage.setItem("token", "");
    window.location.reload();
  };

  // Edit
  return (
    <div
      style={{
        backgroundColor: "#333",
        width: gui.screenWidth,
        height: gui.screenHeight,
        minHeight: 896,
        overflow: "hidden",
        display: "flex",
        position: "relative",
        alignItems: "center",
        flexDirection: "column",
        backgroundImage: `url(${Background})`,
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
      }}
    >
      <ToastContainer />
      <img
        onClick={() => {
          setIsMute((v) => !v);
        }}
        style={{
          position: "absolute",
          right: 80,
          top: 60,
          cursor: "pointer",
          zIndex: 2,
        }}
        src={!isMute ? IconLoa : IconLoaTat}
      />
      <img
        onClick={() => {
          setIsEN((v) => !v);
        }}
        style={{
          position: "absolute",
          right: 40,
          top: 60,
          cursor: "pointer",
          zIndex: 2,
        }}
        src={isEN ? IconEN : IconVI}
      />
      <div
        style={{
          display: "flex",
          zIndex: 1,
          position: "absolute",
          top: 92,
          width: gui.screenWidth,
          justifyContent: "center",
          alignItems: "center",
          left: 0,
          height: "auto",
          flexDirection: "column",
        }}
      >
        <div
          style={{
            zIndex: 10,
            position: "relative",
            height: 105,
          }}
        >
          <img style={{}} src={bangdon} />
          <img
            style={{
              position: "absolute",
              zIndex: 12,
              left: 110,
              top: 25,
            }}
            src={showQua ? Congratulation : LuckyRoulette}
          />
        </div>

        <img
          style={{
            marginTop: -55,
            zIndex: 11,
            width: 390,
            height: 390,
          }}
          src={Vongquay}
        />
        <img
          style={{
            position: "absolute",
            top: -100,
            zIndex: 1,
          }}
          src={HieuUngSao}
        />
      </div>
      <section id="luckywheel" className="hc-luckywheel">
        <div className="hc-luckywheel-container">
          <canvas className="hc-luckywheel-canvas" width="500px" height="500px">
            Vòng Xoay May Mắn
          </canvas>
        </div>

        <img
          style={{
            position: "absolute",
            top: 131,
            left: 134,
            zIndex: 99,
          }}
          src={IconKimQuay}
        />
        <div className="hc-luckywheel-btn">{t("Spin")}</div>

        <div
          style={{
            position: "absolute",
            color: "#FFF",
            width: 210,
            left: 52,
            zIndex: 99,
            bottom: -160,
            fontSize: 14,
            fontWeight: "bold",
            textAlign: "center",
          }}
        >
          {t("you have")} {t("turns")} {countPlayTurn}
        </div>
      </section>
      <div
        style={{
          position: "absolute",
          bottom: 0,
          width: "100%",
          zIndex: 98,
        }}
      >
        <div
          className="ct-flex-row"
          style={{
            marginBottom: 12,
            justifyContent: "space-between",
            paddingLeft: 16,
            paddingRight: 16,
          }}
        >
          <div className="ct-flex-col">
            <ItemOption
              icon={IconSach}
              onClick={(v) => setShowTopStar(true)}
              text={t("TopPlayer")}
            />
            <ItemOption
              icon={IconSach}
              onClick={(v) => setShowHistory(true)}
              text={t("History")}
            />

            {isLogin ? (
              <>
                <ItemOption
                  onClick={() => setBuyMore(true)}
                  icon={IconTui}
                  text={t("Buy more turn")}
                />
                <ItemOption
                  onClick={(v) => setShowNaptien(true)}
                  icon={IconDeposit}
                  text={t("Deposit")}
                />
              </>
            ) : null}
          </div>
          <div className="ct-flex-col">
            <ItemOption
              onClick={(v) => setShowHuongDan(true)}
              icon={IconVuongMieng}
              text={t("Gift")}
            />
            {isLogin ? (
              <>
                <ItemOption
                  onClick={(v) => setShowGetFreeTurn(true)}
                  icon={IconFree}
                  text={t("Dailyfree")}
                />
                <ItemOption
                  onClick={onLogout}
                  icon={logout}
                  text={t("Logout")}
                />
              </>
            ) : (
              <>
                <ItemOption
                  onClick={(v) => setshowDangKi(true)}
                  icon={IconRegister}
                  text={t("Register")}
                />
                <ItemOption
                  onClick={(v) => setShowDangNhap(true)}
                  icon={IconLogin}
                  text={t("Login")}
                />
              </>
            )}
          </div>
        </div>
      </div>
      {showQua ? (
        <PopupQua
          token={tokenState}
          languageUrl={languageUrl}
          data={ItemTrungThuong}
          callback={callbackOk}
        />
      ) : null}
      {buyMore ? (
        <PopupBuyTurn
          onClose={() => setBuyMore(false)}
          onSuccess={(e) => {
            //setBuyMore(false);
            if (e) {
              setMessageError("BuyTurnSucceed");
            } else {
              setMessageError("BuyTurnFail");
            }
          }}
        />
      ) : null}

      {showTopStar ? (
        <PopupTopStar onClose={() => setShowTopStar(false)} />
      ) : null}
      {showHistory ? (
        <PopupHistory onClose={() => setShowHistory(false)} />
      ) : null}
      {showHuongDan ? (
        <PopupHuongDan onClose={() => setShowHuongDan(false)} />
      ) : null}

      {showDangNhap ? (
        <PopupLogin
          onClose={() => setShowDangNhap(false)}
          onSuccess={(e) => {
            if (e) {
              setShowDangNhap(false);
              setMessageError("LoginSucceed");
              setIsLogin(true);
            } else {
              setMessageError("LoginFail");
            }
          }}
        />
      ) : null}
      {showDangKi ? (
        <PopupRegister
          onClose={() => setshowDangKi(false)}
          onSuccess={(e) => {
            setshowDangKi(false);
            if (e) {
              setMessageError("RegisterSucceed");
            } else {
              setMessageError("RegisterFail");
            }
          }}
        />
      ) : null}
      {showNaptien ? (
        <PopupDeposit
          onClose={() => setShowNaptien(false)}
          onSuccess={(e) => {
            // setShowNaptien(false);
            if (e.status === "200") {
              setMessageError("PressClickPayment");
            } else {
              setMessageError("BankingCauseError");
            }
          }}
        />
      ) : null}
      {showGetFreeTurn ? (
        <PopupGetFreeTurn onClose={() => setShowGetFreeTurn(false)} />
      ) : null}
    </div>
  );
};

const ItemOption = ({ icon, text, onClick, type }) => (
  <div
    className="ct-flex-col"
    style={{
      marginTop: 10,
      marginBottom: text ? 0 : 14,
      fontSize: 12,
      color: "#FFF",
      cursor: "pointer",
    }}
    onClick={() => onClick && onClick(type)}
  >
    <img style={{}} src={icon} />
    {text ? text : ""}
  </div>
);

export default App;
