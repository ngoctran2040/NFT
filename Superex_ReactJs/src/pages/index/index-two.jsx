// src/pages/index/index-two.jsx
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import Navbar from '../../components/navbar';
import ProductTwo from '../../components/product/product-two';
import CreaterTwo from '../../components/creater/creater-two';
import PopularCollection from '../../components/popular-collection';
import Footer from '../../components/footer';

import CreateNftForm from '../../components/nft/CreateNftForm';

import TinySlider from 'tiny-slider-react';
import 'tiny-slider/dist/tiny-slider.css';

import { fetchMarketNfts, fetchAllNfts } from '../../api/nftApi';
import { metamaskLogin } from '../../services/metamask';

export default function IndexTwo() {
  const [nfts, setNfts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // MetaMask login state
  const [walletAddress, setWalletAddress] = useState('');
  const [loginUser, setLoginUser] = useState(null);
  const [loginError, setLoginError] = useState(null);
  const [loggingIn, setLoggingIn] = useState(false);

  // ✅ admin check (chấp nhận ADMIN/admin)
  const isAdmin = (loginUser?.role || '').toUpperCase() === 'ADMIN';

  const settings = {
    container: '.tiny-five-item',
    controls: false,
    mouseDrag: true,
    loop: true,
    rewind: true,
    autoplay: true,
    autoplayButtonOutput: false,
    autoplayTimeout: 3000,
    navPosition: 'bottom',
    speed: 400,
    gutter: 12,
    responsive: {
      1025: { items: 5 },
      992: { items: 3 },
      767: { items: 2 },
      320: { items: 1 },
    },
  };

  // ✅ Load NFTs (dùng lại sau khi tạo NFT)
  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);

      let data = await fetchMarketNfts();
      if (!Array.isArray(data) || data.length === 0) {
        data = await fetchAllNfts();
      }

      setNfts(data);
    } catch (err) {
      console.error('Lỗi load NFT:', err);
      setError(err.message || 'Có lỗi xảy ra');
    } finally {
      setLoading(false);
    }
  };

  // ✅ Connect/Login MetaMask
  const handleConnect = async () => {
    try {
      setLoggingIn(true);
      setLoginError(null);

      const { address, user } = await metamaskLogin();
      setWalletAddress(address);
      setLoginUser(user);

      localStorage.setItem('walletAddress', address);
      localStorage.setItem('loginUser', JSON.stringify(user));
    } catch (e) {
      console.error(e);
      setLoginError(e?.message || 'MetaMask login thất bại');
    } finally {
      setLoggingIn(false);
    }
  };

  // ✅ Restore login state khi reload trang
  useEffect(() => {
    const addr = localStorage.getItem('walletAddress');
    const u = localStorage.getItem('loginUser');
    if (addr) setWalletAddress(addr);
    if (u) setLoginUser(JSON.parse(u));
  }, []);

  // ✅ Load NFTs khi vào trang
  useEffect(() => {
    loadData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <>
      <div className="round-effect">
        <div className="primary-round opacity-3"></div>
        <div className="gradient-round opacity-3"></div>
      </div>

      <Navbar />

      <section className="bg-half-174">
        <div className="container-fluid">
          <div className="row">
            <div className="col-12">

              {/* ✅ MetaMask connect/login UI */}
              <div className="d-flex flex-column align-items-center mt-4">
                <button
                  className="btn btn-primary"
                  onClick={handleConnect}
                  disabled={loggingIn}
                >
                  {loggingIn
                    ? 'Đang kết nối...'
                    : (walletAddress ? 'Đã kết nối ví' : 'Connect MetaMask')}
                </button>

                {walletAddress && (
                  <p className="text-muted mt-2 mb-0">
                    Wallet: <b>{walletAddress}</b>
                  </p>
                )}

                {loginUser && (
                  <p className="text-success mt-1 mb-0">
                    Login OK (User id: {loginUser.id || loginUser._id || 'N/A'}){' '}
                    {isAdmin ? '— Role: ADMIN' : '— Role: USER'}
                  </p>
                )}

                {loginError && (
                  <p className="text-danger mt-1 mb-0">
                    {loginError}
                  </p>
                )}
              </div>

              {/* ✅ ADMIN mới thấy form tạo NFT */}
              {walletAddress && (
                <div className="d-flex justify-content-center mt-4">
                  <div style={{ width: '100%', maxWidth: 520 }}>
                    {isAdmin ? (
                      <CreateNftForm
                        walletAddress={walletAddress}
                        onCreated={loadData}
                      />
                    ) : (
                      <div className="alert alert-warning mb-0">
                        Bạn đã kết nối ví nhưng <b>không phải ADMIN</b> nên không có quyền tạo/chỉnh sửa NFT.
                      </div>
                    )}
                  </div>
                </div>
              )}

              {/* Trạng thái loading / error */}
              {loading && (
                <p className="text-center text-muted mt-4">Đang tải NFT...</p>
              )}

              {error && (
                <p className="text-center text-danger mt-4">
                  Lỗi tải dữ liệu: {error}
                </p>
              )}

              {/* Có dữ liệu */}
              {!loading && !error && nfts.length > 0 && (
                <div className="tiny-five-item">
                  <TinySlider settings={settings}>
                    {nfts.slice(0, 8).map((item, index) => (
                      <div className="tiny-slide" key={item.id || item._id || index}>
                        <div className="card bg-white nft-items nft-primary rounded-md shadow-md overflow-hidden mx-2 my-3">
                          <div className="nft-image position-relative overflow-hidden">
                            <Link to={`/item-detail-one/${item.id || item._id}`}>
                              <img
                                src={item.product || item.imageUrl}
                                className="img-fluid"
                                alt={item.name}
                              />
                            </Link>

                            <div className="position-absolute top-0 start-0 m-3">
                              <Link to="#" className="badge badge-link bg-primary">
                                {item.tag || `Token #${item.tokenId || ''}`}
                              </Link>
                            </div>

                            <div className="position-absolute top-0 end-0 m-3">
                              <span className="like-icon shadow-sm">
                                <Link to="#" className="text-muted icon">
                                  <i className="mdi mdi-18px mdi-heart mb-0"></i>
                                </Link>
                              </span>
                            </div>
                          </div>

                          <div className="card-body content position-relative">
                            <Link
                              to={`/item-detail-one/${item.id || item._id}`}
                              className="title text-dark h6"
                            >
                              {item.name}
                            </Link>

                            <div className="d-flex align-items-center justify-content-between mt-3">
                              <div>
                                <small className="mb-0 d-block fw-semibold">
                                  Current Bid:
                                </small>
                                <small className="rate fw-bold">
                                  {(item.value || item.price || 0) + ' ETH'}
                                </small>
                              </div>
                              <Link
                                to={`/item-detail-one/${item.id || item._id}`}
                                className="btn btn-icon btn-pills btn-primary"
                              >
                                <i className="uil uil-shopping-bag"></i>
                              </Link>
                            </div>
                          </div>
                        </div>
                      </div>
                    ))}
                  </TinySlider>
                </div>
              )}

              {/* Không có NFT nào */}
              {!loading && !error && nfts.length === 0 && (
                <p className="text-center text-muted mt-4">
                  Hiện chưa có NFT nào trong hệ thống.
                </p>
              )}
            </div>
          </div>
        </div>

        <div className="container mt-100 mt-60">
          <ProductTwo />
        </div>

        <div className="container mt-100 mt-60">
          <div className="row">
            <div className="col-12">
              <div className="section-title">
                <h4 className="title mb-2">Best Creators &amp; Sellers</h4>
                <p className="text-muted mb-0">Best sellers of the week's NFTs</p>
              </div>
            </div>
          </div>

          <CreaterTwo />
        </div>

        <div className="container mt-100 mt-60">
          <PopularCollection />
        </div>
      </section>

      <Footer />
    </>
  );
}
