import React from 'react';
import {RouteComponentProps} from 'react-router';

type WalkConfirmedProps = {
    success?: string | undefined,
}

function WalkConfirmedScreen({location}: RouteComponentProps<WalkConfirmedProps>) {

    const props = location.state as WalkConfirmedProps;

    return (
        <div>
            <br/>
            <div className="container-page">
                {
                    !!props && !!props.success ?

                        <div className="confirmed">
                            <div className="horizontal-center">
                                Šetnja je potvrđena, sada ju možete vidjeti u svom rasporedu.
                               {/* Molimo koristite kartu ispod kako biste lakše došli do svoje udruge!*/}
                            </div>

                        </div>

                        : (
                            <div className="horizontal-center">Dogodila se greška, pokušajte ponovno prijaviti šetnju!</div>
                        )
                }
            </div>

        </div>
    );
}

export default WalkConfirmedScreen;
